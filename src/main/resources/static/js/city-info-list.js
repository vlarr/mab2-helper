$(document).on('click', '.content-link', function (event) {
        let dataId = $(event.target).attr("data-id")
        let targetElem = $('fieldset[data-id="' + dataId + '"]')
        let container = $('.content-column-right');
        let position = targetElem.offset().top - container.offset().top + container.scrollTop();
        container.animate({
            scrollTop: position
        })
    }
)