$(document).on('click', '.content-link', function (event) {
        // console.log( $( this ) );

        let dataId = $(event.target).attr("data-id")
        let targetElem = $('fieldset[data-id="' + dataId + '"]')
        let container = $('.content-column-right');
        let position = targetElem.offset().top - container.offset().top + container.scrollTop();
        container.animate({
            scrollTop: position
        })

        // container.scrollTop(targetElem.offset().top);
    }
)