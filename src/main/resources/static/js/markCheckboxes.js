$('.modal').on('show.bs.modal', function (event) {
    const button = $(event.relatedTarget);
    const category = button.data('val');
    const modal = $(this);

    // Uncheck all checkboxes
    modal.find('.form-check-input').prop('checked', false);

    // Check the checkbox with the selected category
    modal.find('input[value="' + category + '"]').prop('checked', true);
});