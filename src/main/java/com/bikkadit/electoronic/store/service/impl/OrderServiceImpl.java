package com.bikkadit.electoronic.store.service.impl;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.exception.BadApiRequest;
import com.bikkadit.electoronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electoronic.store.helper.Helper;
import com.bikkadit.electoronic.store.model.*;
import com.bikkadit.electoronic.store.payload.CreateOrderRequest;
import com.bikkadit.electoronic.store.payload.OrderDto;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.repository.CartRepository;
import com.bikkadit.electoronic.store.repository.OrderRepository;
import com.bikkadit.electoronic.store.repository.UserRepository;
import com.bikkadit.electoronic.store.service.OrderServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderServiceI {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepository cartRepository;


    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {

        // Create UserId & CartId From oderDto
        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();
        //find User
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));

        Cart cart = this.cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.size() == 0) {
            throw new BadApiRequest(AppConstants.NOT_VALID_QUANTITY);
        }
        // generate Order

        Order order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingPhoneNo(orderDto.getBillingPhoneNo())
                .billingAddress(orderDto.getBillingAddress())
                .orderDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        AtomicReference<Integer> orderAmount=new AtomicReference<>(0);

        //Convert cartItem to OrderItem
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {

            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();
                    orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
            return orderItem;

        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setTotalAmount(orderAmount.get());

        cart.getItems().clear();
        cartRepository.save(cart);
        Order saveOrder = orderRepository.save(order);

        return this.modelMapper.map(saveOrder, OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));

        this.orderRepository.delete(order);
    }

    @Override
    public List<OrderDto> getAllOrdersOfUser(String userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));

        List<Order> orders = this.orderRepository.findByUser(user);

        List<OrderDto> dtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());

        return dtos;
    }

    @Override
    public PageableResponse<OrderDto> getAllOrder(Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        Sort sort = (direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        PageRequest pages = PageRequest.of(pageNumber, pageSize, sort);

        Page<Order> all = this.orderRepository.findAll(pages);

        return Helper.getPageableResponse(all,OrderDto.class);
    }
}
