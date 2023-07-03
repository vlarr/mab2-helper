$(document).on('click', '.content-link', function (event) {
        let dataId = $(event.target).attr("data-city-id")
        let targetElem = $('fieldset[data-city-id="' + dataId + '"]')
        let container = $('.content-column-right');
        let position = targetElem.offset().top - container.offset().top + container.scrollTop();
        container.animate({
            scrollTop: position
        })
    }
)