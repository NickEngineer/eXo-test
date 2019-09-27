$(document).ready(function () {
    validateProductsVisualization();
});

function validateProductsVisualization() {
    $(".invalid-product").css('background-color', "red");
}

function buyProducts() {

    var validProductItems = "";
    $(".valid-product").each(function () {
        validProductItems += $(this).attr("id") + ",";
    });

    $.post(
        "/buildshop/buyservice",
        {
            selectedGoods: validProductItems
        },
        responseHandler
    );
}

function responseHandler(data, textStatus, xhr) {
    if (xhr.status == 201) {
        //success
        window.location.replace("/buildshop/shop/success");
    } else {
        //failure
        window.location.replace("/buildshop/shop/failure");
    }
}