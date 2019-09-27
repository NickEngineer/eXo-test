$(document).ready(function () {
    getAndSetGoods();
});

function getAndSetGoods() {
    $.post(
        "/buildshop/shop/items",
        setGoods
    );
}

function setGoods(goods) {
    $("#goods").html(goods);
}

function goToBasket() {
    var items = "";

    $("input:checked").each(function () {
        items += $(this).parent().parent().attr("id") + ",";
    });

    getTransfer("/buildshop/shop/basket", items, "GET");
}

function getTransfer(path, params, method) {

    form = document.createElement('form');
    form.method = method;
    form.action = path;

    hiddenField = document.createElement('input');
    hiddenField.type = 'hidden';
    hiddenField.name = "items";
    hiddenField.value = params;

    form.appendChild(hiddenField);

    document.body.appendChild(form);
    form.submit();
}