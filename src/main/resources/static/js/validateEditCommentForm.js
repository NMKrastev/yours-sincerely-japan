function validateEditCommentForm(event) {

    let isValid = true;

    let editCommentContentInput = document.getElementById('editCommentContent');
    let editCommentContentValue = editCommentContentInput.value.trim();
    let editCommentContentAlert = document.getElementById('edit-comment-content-alert');
    let editCommentContentError = document.getElementById('edit-comment-content-error');

    if (!editCommentContentError) {
        editCommentContentError = document.createElement('small');
        editCommentContentError.setAttribute('id', 'edit-comment-content-error');
    }

    editCommentContentInput.classList.remove('is-invalid');
    editCommentContentAlert.innerHTML = '';

    if (editCommentContentValue.length < 1) {
        isValid = false;
        editCommentContentError.textContent = 'Comment length must be at least 1 character long.';
        editCommentContentInput.classList.add('is-invalid');
        editCommentContentAlert.appendChild(editCommentContentError);
        event.preventDefault();
    }

    if (isValid) {
        return true;
    }

    event.preventDefault();
}

document.getElementById('edit-comment-form').addEventListener('submit', validateEditCommentForm);