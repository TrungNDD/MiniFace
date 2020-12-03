(function ($) {
    "use strict";

    /*==================================================================
     [ Focus Contact2 ]*/
    $('.input100').each(function () {
        $(this).on('blur', function () {
            if ($(this).val().trim() != "") {
                $(this).addClass('has-val');
            } else {
                $(this).removeClass('has-val');
            }
        })
    })


    /*==================================================================
     [ Validate after type ]*/
    $('.validate-input .input100').each(function () {
        $(this).on('blur', function () {
            if (validate(this) == false) {
                showValidate(this);
            } else {
                $(this).parent().addClass('true-validate');
            }
        })
    })

    /*==================================================================
     [ Validate ]*/
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit', function () {
        var check = true;

        for (var i = 0; i < input.length; i++) {
            if (validate(input[i]) == false) {
                showValidate(input[i]);
                check = false;
            }
        }

        var password = $('#password').val();
        var confirm = $('#repeat-pass').val();
        if (password.localeCompare(confirm)) {
            alert("Confirm must match password");
            check = false;
        }

        return check;
    });


    $('.validate-form .input100').each(function () {
        $(this).focus(function () {
            hideValidate(this);
            $(this).parent().removeClass('true-validate');
        });
    });

    function validate(input) {
        // validate email
        if ($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if ($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        } else {
            if ($(input).val().trim() == '') {
                return false;
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }

})(jQuery);

// animation grid layout
(function () {

    var bodyEl = document.body,
            docElem = window.document.documentElement,
            support = {transitions: Modernizr.csstransitions},
            // transition end event name
            transEndEventNames = {'WebkitTransition': 'webkitTransitionEnd', 'MozTransition': 'transitionend', 'OTransition': 'oTransitionEnd', 'msTransition': 'MSTransitionEnd', 'transition': 'transitionend'},
            transEndEventName = transEndEventNames[Modernizr.prefixed('transition')],
            onEndTransition = function (el, callback) {
                var onEndCallbackFn = function (ev) {
                    if (support.transitions) {
                        if (ev.target != this)
                            return;
                        this.removeEventListener(transEndEventName, onEndCallbackFn);
                    }
                    if (callback && typeof callback === 'function') {
                        callback.call(this);
                    }
                };
                if (support.transitions) {
                    el.addEventListener(transEndEventName, onEndCallbackFn);
                } else {
                    onEndCallbackFn();
                }
            },
            gridEl = document.getElementById('theGrid'),
            sidebarEl = document.getElementById('theSidebar'),
            gridItemsContainer = gridEl.querySelector('section.grid'),
            contentItemsContainer = gridEl.querySelector('section.content'),
            gridItems = gridItemsContainer.querySelectorAll('.grid__item'),
            contentItems = contentItemsContainer.querySelectorAll('.content__item'),
            closeCtrl = contentItemsContainer.querySelector('.close-button'),
            current = -1,
            lockScroll = false,
            xscroll, yscroll,
            isAnimating = false,
            menuCtrl = document.getElementById('menu-toggle'),
            menuCloseCtrl = sidebarEl.querySelector('.close-button');

    /**
     * gets the viewport width and height
     * based on http://responsejs.com/labs/dimensions/
     */
    function getViewport(axis) {
        var client, inner;
        if (axis === 'x') {
            client = docElem['clientWidth'];
            inner = window['innerWidth'];
        } else if (axis === 'y') {
            client = docElem['clientHeight'];
            inner = window['innerHeight'];
        }

        return client < inner ? inner : client;
    }

    function scrollX() {
        return window.pageXOffset || docElem.scrollLeft;
    }

    function scrollY() {
        return window.pageYOffset || docElem.scrollTop;
    }

    function init() {
        initEvents();
    }

    function initEvents() {
        [].slice.call(gridItems).forEach(function (item, pos) {
            // grid item click event
            item.addEventListener('click', function (ev) {
                ev.preventDefault();
                if (isAnimating || current === pos) {
                    return false;
                }
                isAnimating = true;
                // index of current item
                current = pos;
                // simulate loading time..
                classie.add(item, 'grid__item--loading');
                setTimeout(function () {
                    classie.add(item, 'grid__item--animate');
                    // reveal/load content after the last element animates out (todo: wait for the last transition to finish)
                    setTimeout(function () {
                        loadContent(item);
                    }, 500);
                }, 1000);
            });
        });

        closeCtrl.addEventListener('click', function () {
            // hide content
            hideContent();
        });

        // keyboard esc - hide content
        document.addEventListener('keydown', function (ev) {
            if (!isAnimating && current !== -1) {
                var keyCode = ev.keyCode || ev.which;
                if (keyCode === 27) {
                    ev.preventDefault();
                    if ("activeElement" in document)
                        document.activeElement.blur();
                    hideContent();
                }
            }
        });

        // hamburger menu button (mobile) and close cross
        menuCtrl.addEventListener('click', function () {
            if (!classie.has(sidebarEl, 'sidebar--open')) {
                classie.add(sidebarEl, 'sidebar--open');
            }
        });

        menuCloseCtrl.addEventListener('click', function () {
            if (classie.has(sidebarEl, 'sidebar--open')) {
                classie.remove(sidebarEl, 'sidebar--open');
            }
        });
    }

    function loadContent(item) {
        // add expanding element/placeholder 
        var dummy = document.createElement('div');
        dummy.className = 'placeholder';

        // set the width/heigth and position
        dummy.style.WebkitTransform = 'translate3d(' + (item.offsetLeft - 5) + 'px, ' + (item.offsetTop - 5) + 'px, 0px) scale3d(' + item.offsetWidth / gridItemsContainer.offsetWidth + ',' + item.offsetHeight / getViewport('y') + ',1)';
        dummy.style.transform = 'translate3d(' + (item.offsetLeft - 5) + 'px, ' + (item.offsetTop - 5) + 'px, 0px) scale3d(' + item.offsetWidth / gridItemsContainer.offsetWidth + ',' + item.offsetHeight / getViewport('y') + ',1)';

        // add transition class 
        classie.add(dummy, 'placeholder--trans-in');

        // insert it after all the grid items
        gridItemsContainer.appendChild(dummy);

        // body overlay
        classie.add(bodyEl, 'view-single');

        setTimeout(function () {
            // expands the placeholder
            dummy.style.WebkitTransform = 'translate3d(-5px, ' + (scrollY() - 5) + 'px, 0px)';
            dummy.style.transform = 'translate3d(-5px, ' + (scrollY() - 5) + 'px, 0px)';
            // disallow scroll
            window.addEventListener('scroll', noscroll);
        }, 25);

        onEndTransition(dummy, function () {
            // add transition class 
            classie.remove(dummy, 'placeholder--trans-in');
            classie.add(dummy, 'placeholder--trans-out');
            // position the content container
            contentItemsContainer.style.top = scrollY() + 'px';
            // show the main content container
            classie.add(contentItemsContainer, 'content--show');
            // show content item:
            classie.add(contentItems[current], 'content__item--show');
            // show close control
            classie.add(closeCtrl, 'close-button--show');
            // sets overflow hidden to the body and allows the switch to the content scroll
            classie.addClass(bodyEl, 'noscroll');

            isAnimating = false;
        });
    }

    function hideContent() {
        var gridItem = gridItems[current],
                contentItem = contentItems[current];

        classie.remove(contentItem, 'content__item--show');
        classie.remove(contentItemsContainer, 'content--show');
        classie.remove(closeCtrl, 'close-button--show');
        classie.remove(bodyEl, 'view-single');

        setTimeout(function () {
            var dummy = gridItemsContainer.querySelector('.placeholder');

            classie.removeClass(bodyEl, 'noscroll');

            dummy.style.WebkitTransform = 'translate3d(' + gridItem.offsetLeft + 'px, ' + gridItem.offsetTop + 'px, 0px) scale3d(' + gridItem.offsetWidth / gridItemsContainer.offsetWidth + ',' + gridItem.offsetHeight / getViewport('y') + ',1)';
            dummy.style.transform = 'translate3d(' + gridItem.offsetLeft + 'px, ' + gridItem.offsetTop + 'px, 0px) scale3d(' + gridItem.offsetWidth / gridItemsContainer.offsetWidth + ',' + gridItem.offsetHeight / getViewport('y') + ',1)';

            onEndTransition(dummy, function () {
                // reset content scroll..
                contentItem.parentNode.scrollTop = 0;
                gridItemsContainer.removeChild(dummy);
                classie.remove(gridItem, 'grid__item--loading');
                classie.remove(gridItem, 'grid__item--animate');
                lockScroll = false;
                window.removeEventListener('scroll', noscroll);
            });

            // reset current
            current = -1;
        }, 25);
    }

    function noscroll() {
        if (!lockScroll) {
            lockScroll = true;
            xscroll = scrollX();
            yscroll = scrollY();
        }
        window.scrollTo(xscroll, yscroll);
    }

    init();

})();

// pagination code goes here
function template(data) {
    var grid = '';
    console.log(data);
    if (data.length == 0) {
        grid = "No records found";
    } else {
        $.each(data, function (index, article) {
            grid += `<a class="grid__item" href="viewArticle?idArticle=` + article.idArticle + `">
		<h2 class="title title--preview">` + article.title + `</h2>
                <img src='${article.image}' style='width: 100%; heigth: auto'/>
		<div class="loader"></div>
		<span class="category">by ` + article.poster + `</span>
		<div class="meta meta--preview">
			<span class="meta__date"><i class="fa fa-calendar-o"></i>` + article.date + `</span>
			<span class="meta__reading-time"><i class="fa fa-clock-o"></i> 3 min read</span>
		</div>
    </a>`;
        });
    }
    return grid;
}

// change label when a file is chosen
$('input[type="file"]').change(function (e) {
    var fileName = e.target.files[0].name;
    $('#file-upload-label').html(fileName);
});


// paginationjs
var dataContainer = $('#theGrid');

function invokePagination(source) {
    dataContainer.pagination({
        // get the source here
        dataSource: source,
        locator: 'articles',
        totalNumberLocator: function (response) {
            return response.length;
        },
        // edit page size
        pageSize: 20,
        className: 'custom-paginationjs',
        callback: function (data, pagination) {
            $('.grid').html(template(data));
        },
        alias: {
            pageSize: "pageSize",
            pageNumber: "pageNo"
        }
    });
}

invokePagination('http://localhost:8084/J3LP0010_MiniFace/showroom');

// function when searching
function searchArticle() {
    // get value from first input element cause this is search text
    var txtSearch = $("input").first().val().trim();
    console.log(txtSearch);
    invokePagination(`http://localhost:8084/J3LP0010_MiniFace/searchArticle?txtSearch=${txtSearch}`);
    
    // this is to prevent bug lost paging of notif after searching
    notif_paging();
}


// when click search
$('#btnSearch').click(searchArticle);

// or press enter
$('#txtSearch').on('keyup', function (event) {
    if (event.key === 'Enter' || event.keyCode === 13) {
        searchArticle();
    }
});

// pagination for notification

// template for paging
function paging(data) {
    var content = '';
    if (data.length == 0) {
        content = `<a>No notification yet</a>`;
    } else {
        $.each(data, function (index, notif) {
            content += `<a href="viewArticle?idArticle=${notif.idArticle}">
                            ${notif.message} (${notif.date})</a>`;
        });
    }
    return content;
}

var idAccount = $('#idAccount').html();

function notif_paging() {
    $('.dropdown').pagination({
        dataSource: function (done) {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8084/J3LP0010_MiniFace/notification',
                success: function (response) {
                    done(response);
                }
            });
        },
        pageSize: 5,
        className: 'custom-paginationjs notif-paging',
        callback: function (data, pagination) {
            $('.dropdown-content').html(paging(data));
        }
    })
}

notif_paging();

