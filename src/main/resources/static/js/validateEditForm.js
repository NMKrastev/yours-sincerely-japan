function validateEditForm(event) {

    let isValid = true;

    let fullNameInput = document.getElementById('fullName');
    let fullNameValue = fullNameInput.value.trim();
    let fullNameAlert = document.getElementById('full-name-alert');
    let smallFullName = document.getElementById('full-name-error');

    if (!smallFullName) {
        smallFullName = document.createElement('small');
        smallFullName.setAttribute('id', 'full-name-error');
    }

    fullNameInput.classList.remove('is-invalid');
    fullNameAlert.innerHTML = '';

    if (fullNameValue.length < 1 || fullNameValue.length > 30) {
        isValid = false;
        smallFullName.textContent = 'Full name must be between 1 and 30 characters.';
        fullNameInput.classList.add('is-invalid');
        fullNameAlert.appendChild(smallFullName);
        event.preventDefault();
    }

    let emailInput = document.getElementById('email');
    let emailValue = emailInput.value.trim();
    let emailAlert = document.getElementById('email-alert');
    let emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    let smallEmail = document.getElementById('email-error');

    if (!smallEmail) {
        smallEmail = document.createElement('small');
        smallEmail.setAttribute('id', 'email-error');
    }

    emailInput.classList.remove('is-invalid');
    emailAlert.innerHTML = '';

    if (!emailRegex.test(emailValue)) {
        isValid = false;
        smallEmail.textContent = 'Enter a valid email address.';
        emailInput.classList.add('is-invalid');
        emailAlert.appendChild(smallEmail);
        event.preventDefault();
    }
}

document.getElementById('edit-form').addEventListener('submit', validateEditForm);