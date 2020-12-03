function sendReactionToServer(emotion) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log("reacted");
        }
    };
    // get idArticle and let ajax send request to server
    var idArticle = document.getElementById("idArticle").innerHTML;
    xhttp.open("POST", `reaction?idArticle=${idArticle}&emotion=${emotion}`, true);
    xhttp.send();
}

function like() {
    var like = $('#like');

    // if this post has already been liked by user
    if (like.hasClass("disable")) {
        console.log("like disabled");
        return;
    }

    // increment counter
    var likeCount = $('#likeCount').html();
    $('#likeCount').html(++likeCount);

    // make icon change accordingly
    like.addClass("fa-thumbs-up disable");
    like.removeClass("fa-thumbs-o-up");

    // do the undislike if article has already been unlike
    if ($('#dislike').hasClass('disable'))
        undislike();

    // send request to server
    sendReactionToServer('like');
}

function dislike() {
    var dislike = $('#dislike');
    // if this post has already been disliked by user
    if (dislike.hasClass("disable")) {
        console.log("dislike disabled");
        return;
    }

    // increment counter
    var dislikeCount = $('#dislikeCount').html();
    $('#dislikeCount').html(++dislikeCount);

    // make icon change accordingly
    $('#dislike').addClass("fa-thumbs-down disable");
    $('#dislike').removeClass("fa-thumbs-o-down");

    // do the unlike if article has already been undisslike
    if ($('#like').hasClass('disable'))
        unlike();

    //send request to server
    sendReactionToServer('dislike');
}

function unlike() {
    var likeCount = $('#likeCount').html();
    $('#likeCount').html(--likeCount);
    $('#like').removeClass("fa-thumbs-up disable");
    $('#like').addClass("fa-thumbs-o-up");
}

function undislike() {
    var dislikeCount = $('#dislikeCount').html();
    $('#dislikeCount').html(--dislikeCount);
    $('#dislike').removeClass("fa-thumbs-down disable");
    $('#dislike').addClass("fa-thumbs-o-down");
}

$('#like').click(like);
$('#dislike').click(dislike);

// COMMENT
// draw comment to UI, called when make comment successfully
function addCommentToUI(comment, idComment) {
    var idArticle = $('#idArticle').html();
    var idCommenter = $('#email').html();
    //console.log(`${idCommenter} commented!`);

    // add this comment to UI
    var container = $('.comments-container').eq(0);
    //console.log(container);

    // get current date format
    var today = new Date();
    var dd = today.getDate();

    var mm = today.getMonth() + 1;
    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }

    if (mm < 10) {
        mm = '0' + mm;
    }
    var curDate = yyyy + '-' + mm + '-' + dd;

    container.append(`
        <div class="comment">
            <div class="commenter">
                <i class="fa fa-user-circle-o"></i>
                ${idCommenter} - (${curDate})
            <a href="delete?action=comment&idArticle=${idArticle}&idComment=${idComment}" class="fa fa-ban newDelete" style="float: right"></a>
            </div>
            ${comment}
        </div>
    `);

    // add delete confirm on click event
    $('.newDelete').click(function () {
        return confirm("Are you sure to delete this?");
    });
}

// send comment to server
function makeComment(comment) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // update UI
            console.log(this.responseText);
            addCommentToUI(comment, this.responseText);
        }
    };

    // send information to server
    var idArticle = document.getElementById("idArticle").innerHTML;
    xhttp.open("POST", `comment?idArticle=${idArticle}&content=${comment}`, true);
    xhttp.send();
}
;

$('#input-comment').on('keyup', function (event) {
    if (event.key === 'Enter' || event.keyCode === 13) {
        // get comment input
        var comment = $('#input-comment').val();

        if (comment.length == 0) {
            return;
        }
        // send comment input to server
        makeComment(comment);

        // clear input
        $('#input-comment').val("");
    }
});

// confirmation before deleting
$('.delete').click(function () {
    return confirm("Are you sure to delete this?");
});