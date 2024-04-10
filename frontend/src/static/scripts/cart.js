// This is a placeholder for your actual course data
var courses = [
    { id: 1, name: 'Course 1', price: 100 },
    { id: 2, name: 'Course 2', price: 200 },
    // ...
];

var cart = [];

function addToCart(event) {
    var courseId = event.target.dataset.courseId;
    var course = courses.find(function(course) {
        return course.id == courseId;
    });

    cart.push(course);
    updateCartDisplay();
}

function updateCartDisplay() {
    var cartItemsDiv = document.querySelector('.cart-items');
    cartItemsDiv.innerHTML = '';

    cart.forEach(function(course) {
        var courseDiv = document.createElement('div');
        courseDiv.textContent = course.name + ' - ' + course.price;
        cartItemsDiv.appendChild(courseDiv);
    });
}

var addToCartButtons = document.querySelectorAll('.add-to-cart');
addToCartButtons.forEach(function(button) {
    button.addEventListener('click', addToCart);
});