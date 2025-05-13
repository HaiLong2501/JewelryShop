/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

const swiper_brand = new Swiper('#swiper-brand', {
    // Optional parameters
    slidesPerView: 3, 
    spaceBetween: 10,
    autoplay: {
        delay: 3000,
        disableOnInteraction: false
    },
    direction: 'horizontal',
    loop: true,

    // If we need pagination
    pagination: {
        el: '#swiper-brand .swiper-pagination',
        clickable: true
    },

    // Navigation arrows
    navigation: {
        nextEl: '#swiper-brand .swiper-button-next',
        prevEl: '#swiper-brand .swiper-button-prev',
    },

    // And if we need scrollbar
    scrollbar: {
        el: '#swiper-brand .swiper-scrollbar',
    },
});

const swiper_product = new Swiper('#swiper_Product', {
    // Optional parameters
    slidesPerView: 4, 
    spaceBetween: 20,
    autoplay: {
        delay: 5000,
        disableOnInteraction: false
    },
    direction: 'horizontal',
    loop: true,

    // If we need pagination
    pagination: {
        el: '#swiper_Product .swiper-pagination',
        clickable: true
    },

    // Navigation arrows
    navigation: {
        nextEl: '#swiper_Product .swiper-button-next',
        prevEl: '#swiper_Product .swiper-button-prev',
    },

    // And if we need scrollbar
    scrollbar: {
        el: '#swiper_Product .swiper-scrollbar',
    },
});
