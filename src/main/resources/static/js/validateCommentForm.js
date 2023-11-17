function validateCommentForm(event) {

    let isValid = true;

    let commentContentInput = document.getElementById('commentContent');
    let commentContentValue = commentContentInput.value.trim();
    let commentContentAlert = document.getElementById('comment-content-alert');
    let commentContentError = document.getElementById('comment-content-error');

    if (!commentContentError) {
        commentContentError = document.createElement('small');
        commentContentError.setAttribute('id', 'comment-content-error');
    }

    commentContentInput.classList.remove('is-invalid');
    commentContentAlert.innerHTML = '';

    if (commentContentValue.length < 1) {
        isValid = false;
        commentContentError.textContent = 'Comment length must be at least 1 character long.';
        commentContentInput.classList.add('is-invalid');
        commentContentAlert.appendChild(commentContentError);
        event.preventDefault();
    }

    if (isValid) {
        return true;
    }

    event.preventDefault();
}

document.getElementById('comment-form').addEventListener('submit', validateCommentForm);