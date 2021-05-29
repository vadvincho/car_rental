const url = 'http://localhost:8081/users';
const modalWrapper = document.querySelector('.modal-wrapper');
// modal add
const addModal = document.querySelector('.add-modal');
const addModalForm = document.querySelector('.add-modal .form');
// modal edit
const editModal = document.querySelector('.edit-modal');
const editModalForm = document.querySelector('.edit-modal .form');

const btnAdd = document.querySelector('.btn-add');
const btnGetAllUsers = document.querySelector('.btn-getAll');
const tableUsers = document.querySelector('.table-users');

let id;

// Create element and render users
const renderUsers = (users) => {
    tableUsers.innerHTML='';
    for (let userKey in users) {
        const tr = `
    <tr data-id='${users[userKey].id}'>
      <td>${users[userKey].id}</td>
      <td>${users[userKey].login}</td>
      <td>${users[userKey].email}</td>
      <td>${users[userKey].role.name}</td>
      <td>
        <button class="btn btn-edit">Edit</button>
        <button class="btn btn-delete">Delete</button>
      </td>
    </tr>
  `;
        tableUsers.insertAdjacentHTML('beforeend', tr);

        // Click edit user
        const btnEdit = document.querySelector(`[data-id='${users[userKey].id}'] .btn-edit`);
        btnEdit.addEventListener('click', () => {
            editModal.classList.add('modal-show');
            id = users[userKey].id;
            editModalForm.name.value = users[userKey].login;
            editModalForm.email.value = users[userKey].email;
            editModalForm.list1.value = users[userKey].role.name;
        });

        // Click delete user
        const btnDelete = document.querySelector(`[data-id='${users[userKey].id}'] .btn-delete`);
        btnDelete.addEventListener('click', () => {
            fetch(`${url}/${users[userKey].id}`, {
                method: 'DELETE'
            })
                .then(res => res.json())
                .then(()=>location.reload())
            });
    }
}


// Click submit in add modal
addModalForm.addEventListener('submit', e => {
    e.preventDefault();
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            login: addModalForm.name.value,
            password: addModalForm.password.value,
            email: addModalForm.email.value
        })
    })
        .then(res => res.json())
    addModal.classList.remove('modal-show');
});

// Click submit in edit modal
editModalForm.addEventListener('submit', e => {
    e.preventDefault();
    console.log(editModalForm.list1.value);
    fetch(url, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: id,
            login: editModalForm.name.value,
            email: editModalForm.email.value,
            role: {id: editModalForm.list1.value}
        })
    })
        .then(res => res.json())
    editModal.classList.remove('modal-show');
});

// Click add user button
btnAdd.addEventListener('click', () => {
    addModal.classList.add('modal-show');
    addModalForm.name.value = '';
    addModalForm.password.value = '';
    addModalForm.email.value = '';
});

btnGetAllUsers.addEventListener('click', () => {
    fetch(url, {
        method: 'GET',
    })
        .then(res => res.json())
        .then(data => {
            renderUsers(data);
        })
});

// User click anyware outside the modal
window.addEventListener('click', e => {
    if (e.target === addModal) {
        addModal.classList.remove('modal-show');
    }
    if (e.target === editModal) {
        editModal.classList.remove('modal-show');
    }
});