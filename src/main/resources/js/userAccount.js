let table = new DataTable('#usersTable', {
    // options
    searching: false
});

function handleDeleteUserModal() {
    let deleteUserModal = document.querySelector('#deleteUserModal');
    if(!deleteUserModal) return;

    deleteUserModal.addEventListener('show.bs.modal', event => {
        // Button that triggered the modal
        const button = event.relatedTarget;
        // Extract info from data-bs-* attributes
        const userId = button.getAttribute('data-bs-user-id');
        const userEmail = button.getAttribute('data-bs-user-email');
        // If necessary, you could initiate an Ajax request here
        // and then do the updating in a callback.

        // Update the modal's content.
        const modalBodyInput = deleteUserModal.querySelector('.modal-body .deleteUserMsg');
        modalBodyInput.textContent = `Are you sure you want to delete user: ${userEmail}`;
        modalBodyInput.value = userId;
    })
}

function handleDeleteUserClick() {
    let deleteUserBtn = document.querySelector('#deleteUserBtn');
    if(!deleteUserBtn) return;
    deleteUserBtn.addEventListener('click', async () => {
        let deleteUserModal = document.querySelector('#deleteUserModal');
        const userId = deleteUserModal.querySelector('.modal-body .deleteUserMsg').value;
        console.log('USER ID: ' + userId);
        let csrfToken = document.querySelector('[name~=_csrf]').value;
        if(!userId) return;
        const url = `/api/user/delete?id=${userId}`;
        try {
            const res = await fetch(url, {
                method: 'DELETE',
                headers: {
                    'X-CSRF-TOKEN': csrfToken
                },
                credentials: 'include'
            });
            if(res) window.location.href = '/user/account';
        } catch(error) {
            console.log(error);
        }
    });
}
document.addEventListener('DOMContentLoaded', () => {
    handleDeleteUserModal();
    handleDeleteUserClick();
})