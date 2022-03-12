const url = 'http://localhost:8080/api/admin/'
const urlRoles = 'http://localhost:8080/api/admin/role/'
const addUserForm = document.getElementById('addUserForm')

const editForm = document.getElementById('editForm')
const deleteForm = document.getElementById('deleteForm')

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}



function showRoleList(target) {
    fetch(urlRoles)
        .then(res => res.json())
        .then(roles => {
            let roleList = ''
            roles.forEach(role => {
                roleList += `<option value="${role.id}">${role.name}</option>`
            })
            target.innerHTML = roleList
        })
}
function getSelectedValues(select) {
    let result = []
    let options = select && select.options
    let opt
    for (let i = 0, iLen = options.length; i < iLen; i++) {
        opt = options[i]
        if(opt.selected){
            result.push(parseInt(opt.value))
        }
    }
    return result
}
function showEditModal(id) {
    const idValue = document.getElementById('ID')
    const firstNameValue = document.getElementById('editedFirstName')
    const lastNameValue = document.getElementById('editedLastName')
    const ageValue = document.getElementById('editedAge')
    const emailValue = document.getElementById('editedEmail')
    const passwordValue = document.getElementById('editedPassword')
    const roleValue = document.getElementById('editedRoles')

    fetch(url + id)
        .then(res => res.json())
        .then(user => {
            idValue.value = user.id
            firstNameValue.value = user.firstName
            lastNameValue.value = user.lastName
            ageValue.value = user.age
            emailValue.value = user.email
            passwordValue.value = user.password
            showRoleList(roleValue)
        })
}
function showDeleteModal(id) {
    const idValue = document.getElementById('deleteID')
    const firstNameValue = document.getElementById('deletedFirstName')
    const lastNameValue = document.getElementById('deletedLastName')
    const ageValue = document.getElementById('deletedAge')
    const emailValue = document.getElementById('deletedEmail')
    const roleValue = document.getElementById('deletedRoles')

    fetch(url + id)
        .then(res => res.json())
        .then(user => {
            idValue.value = user.id
            firstNameValue.value = user.firstName
            lastNameValue.value = user.lastName
            ageValue.value = user.age
            emailValue.value = user.email
            showRoleList(roleValue)
        })
}

const userTableBuild = () => {
    const userTableBody = document.getElementById('userTableBody')
    let result = ''
    fetch(url)
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                let roles = user.roles.map(obj =>`${obj.name}`).join(", ")
                result +=  `<tr>
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${roles}</td>
                            <td>
                                <button type="submit" class="btnEdit btn btn-primary" data-toggle="modal" data-target="#editModal">
                                    Edit
                                </button>
                            </td>
                            <td>
                                <button type="submit" class="btnDelete btn btn-danger" data-toggle="modal" data-target="#deleteModal">
                                    Delete
                                </button>
                            </td>
                        </tr>`
            })
            userTableBody.innerHTML = result
        })

}
on(document, 'click', '.btnEdit', e => {

    e.preventDefault()
    let target = e.target.parentNode.parentNode

    let idUserForEdit = target.children[0].innerHTML

    showEditModal(idUserForEdit)
})
on(document, 'click', '.btnDelete', e => {

    e.preventDefault()
    let target = e.target.parentNode.parentNode

    let idUserForDelete = target.children[0].innerHTML

    showDeleteModal(idUserForDelete)
})
fetch(url)
    .then(res => res.json())
    .then(data => userTableBuild(data))

showRoleList(document.getElementById('roles'))


addUserForm.addEventListener('submit', (e) => {
    e.preventDefault()
    const firstNameValue = document.getElementById('firstName')
    const lastNameValue = document.getElementById('lastName')
    const ageValue = document.getElementById('age')
    const emailValue = document.getElementById('email')
    const passwordValue = document.getElementById('password')
    const roleValue = document.getElementById('roles')
    let setRoles = getSelectedValues(roleValue)
    fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                firstName: firstNameValue.value,
                lastName: lastNameValue.value,
                age: ageValue.value,
                email: emailValue.value,
                password: passwordValue.value,
                roles: setRoles
            })
        }
    )
        .then(res => res.json())
        .then(data => {
            const dataArr = []
            dataArr.push(data)
            userTableBuild(dataArr)
        })
    firstNameValue.value = ''
    lastNameValue.value = ''
    ageValue.value = ''
    emailValue.value = ''
    passwordValue.value = ''

})
editForm.addEventListener('submit', (e) => {
    e.preventDefault()
    const id = document.getElementById('ID')
    const firstNameValue = document.getElementById('editedFirstName')
    const lastNameValue = document.getElementById('editedLastName')
    const ageValue = document.getElementById('editedAge')
    const emailValue = document.getElementById('editedEmail')
    const passwordValue = document.getElementById('editedPassword')
    const roleValue = document.getElementById('editedRoles')
    let setRoles = getSelectedValues(roleValue)
    fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: id.value,
                firstName: firstNameValue.value,
                lastName: lastNameValue.value,
                age: ageValue.value,
                email: emailValue.value,
                password: passwordValue.value,
                roles: setRoles
            })
        }
    )
        .then(res => res.json())
        .then(data => {
            const dataArr = []
            dataArr.push(data)
            userTableBuild(dataArr)
        })
    $('#editModal').modal('hide')
})
deleteForm.addEventListener('submit', (e) => {
    e.preventDefault()
    const id = document.getElementById('deleteID').value
    fetch(url + id, {
        method:'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(() => {
        fetch(url)
            .then(res => res.json())
            .then(data => userTableBuild(data))})
    $('#deleteModal').modal('hide')
})
