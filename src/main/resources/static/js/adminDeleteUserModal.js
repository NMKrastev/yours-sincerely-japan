$('.modal').on('show.bs.modal', function (event) {
    const button = $(event.relatedTarget);
    const username = button.data('username');
    const userId = button.data('id');
    const modal = $(this);

    modal.find('#username').text(username);

    const form = modal.find('#delete');
    const originalAction = form.attr('action');
    const newAction = originalAction + userId;
    form.attr('action', newAction);
});