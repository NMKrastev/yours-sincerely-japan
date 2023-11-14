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

    let roleCheckboxFieldset = document.getElementById("fieldset");
    let roleCheckboxes = document.querySelectorAll('input[type="checkbox"]');
    let roleCheckedCheckboxes = Array.from(roleCheckboxes).filter(checkbox => checkbox.checked);
    let roleCheckboxAlert = document.getElementById('role-alert');
    let roleCheckboxError = document.getElementById('role-error');
    let userRoleValue = "2";

    if (!roleCheckboxError) {
        roleCheckboxError = document.createElement('small');
        roleCheckboxError.setAttribute('id', 'checkbox-error');
    }

    roleCheckboxFieldset.classList.remove("is-invalid");
    roleCheckboxAlert.innerHTML = '';

    let userRoleSelected = Array.from(roleCheckboxes).some(checkbox => checkbox.value === userRoleValue && checkbox.checked);

    if (roleCheckedCheckboxes.length === 0 || !userRoleSelected) {
        isValid = false;
        roleCheckboxError.textContent = '\"USER\" role must always be selected.';
        roleCheckboxFieldset.classList.add('is-invalid');
        roleCheckboxAlert.appendChild(roleCheckboxError);
        event.preventDefault();
    }

    if (isValid) {
        return true;
    }

    event.preventDefault();
}

document.getElementById('edit-form').addEventListener('submit', validateEditForm);