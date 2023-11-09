function validateNewArticleForm(event) {

    let isValid = true;

    let articleTitleInput = document.getElementById('articleTitle');
    let articleTitleValue = articleTitleInput.value.trim();
    let newPostAlert = document.getElementById('new-post-alert');
    let articleTitleError = document.getElementById('article-title-error');

    if (!articleTitleError) {
        articleTitleError = document.createElement('small');
        articleTitleError.setAttribute('id', 'article-title-error');
    }

    articleTitleInput.classList.remove('is-invalid');
    newPostAlert.innerHTML = '';

    if (articleTitleValue.length < 1 || articleTitleValue.length > 50) {
        isValid = false;
        articleTitleError.textContent = 'Title must be between 1 and 50 characters.';
        articleTitleInput.classList.add('is-invalid');
        newPostAlert.appendChild(articleTitleError);
        event.preventDefault();
    }

    let articleContentInput = document.getElementById('articleContent');
    let articleContentValue = articleContentInput.value.trim();
    let articleContentAlert = document.getElementById('article-content-alert');
    let articleContentError = document.getElementById('article-content-error');

    if (!articleContentError) {
        articleContentError = document.createElement('small');
        articleContentError.setAttribute('id', 'email-error');
    }

    articleContentInput.classList.remove('is-invalid');
    articleContentAlert.innerHTML = '';

    if (articleContentValue.length < 20) {
        isValid = false;
        articleContentError.textContent = 'Your story must be at least 20 characters.';
        articleContentInput.classList.add('is-invalid');
        articleContentAlert.appendChild(articleContentError);
        event.preventDefault();
    }

    let articleCheckboxFieldset = document.getElementById("fieldset");
    let articleCheckboxes = document.querySelectorAll('input[type="checkbox"]');
    let articleCheckedCheckboxes = Array.from(articleCheckboxes).filter(checkbox => checkbox.checked);
    let articleCheckboxAlert = document.getElementById('checkbox-alert');
    let articleCheckboxError = document.getElementById('checkbox-error');

    if (!articleCheckboxError) {
        articleCheckboxError = document.createElement('small');
        articleCheckboxError.setAttribute('id', 'checkbox-error');
    }

    articleCheckboxFieldset.classList.remove("is-invalid");
    articleCheckboxAlert.innerHTML = '';

    if (articleCheckedCheckboxes.length === 0) {
        isValid = false;
        articleCheckboxError.textContent = 'You must select at least one category.';
        articleCheckboxFieldset.classList.add('is-invalid');
        articleCheckboxAlert.appendChild(articleCheckboxError);
        event.preventDefault();
    }

    if (isValid) {
        return true;
    }

    event.preventDefault();
}

document.getElementById('new-post-form').addEventListener('submit', validateNewArticleForm);