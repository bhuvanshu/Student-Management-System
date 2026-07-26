// Get HTML elements
const studentForm = document.getElementById("studentForm");
const message = document.getElementById("message");
const getAllBtn = document.getElementById("getAllBtn");
const editForm = document.getElementById("editForm");
const cancelEdit = document.getElementById("cancelEdit");
const editHeading = document.getElementById("editHeading");

// Store the ID of student being edited
let currentEditingStudentId = null;

// ====== ADD STUDENT (POST REQUEST) ======
studentForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    // Collect student data from form
    const student = {
        name: document.getElementById("name").value,
        email: document.getElementById("email").value,
        course: document.getElementById("course").value
    };

    try {
        // Send POST request to create new student
        const response = await fetch(
            "http://localhost:8080/api/students",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(student)
            }
        );

        if (!response.ok) {
            throw new Error("Could not add student");
        }

        const savedStudent = await response.json();
        showMessage(`Student added successfully with ID: ${savedStudent.id}`);
        studentForm.reset();

    } catch (error) {
        showMessage(`Error: ${error.message}`);
    }
});

// ====== GET ALL STUDENTS (GET REQUEST) ======
getAllBtn.addEventListener("click", async function() {
    try {
        // Send GET request to fetch all students
        const response = await fetch("http://localhost:8080/api/students");
        
        if (!response.ok) throw new Error("Failed to fetch students");

        const students = await response.json();
        const tbody = document.getElementById("studentTableBody");
        tbody.innerHTML = "";

        // Create table rows with Edit and Delete buttons
        students.forEach(student => {
            tbody.innerHTML += `
                <tr>
                    <td>${student.id}</td>
                    <td>${student.name}</td>
                    <td>${student.email}</td>
                    <td>${student.course}</td>
                    <td>
                        <button onclick="openEditForm(${student.id}, '${student.name}', '${student.email}', '${student.course}')">Edit</button>
                        <button onclick="deleteStudent(${student.id})">Delete</button>
                    </td>
                </tr>
            `;
        });

        // Show the table
        document.getElementById("studentTable").style.display = "table";
        document.getElementById("studentListHeading").style.display = "block";

    } catch (error) {
        showMessage(`Error: ${error.message}`);
    }
});

// ====== OPEN EDIT FORM ======
function openEditForm(id, name, email, course) {
    // Store student ID being edited
    currentEditingStudentId = id;

    // Fill form with student data
    document.getElementById("editName").value = name;
    document.getElementById("editEmail").value = email;
    document.getElementById("editCourse").value = course;

    // Show edit form
    editForm.style.display = "block";
    editHeading.style.display = "block";
}

// ====== CLOSE EDIT FORM ======
function closeEditForm() {
    editForm.style.display = "none";
    editHeading.style.display = "none";
    currentEditingStudentId = null;
}

// Cancel button handler
cancelEdit.addEventListener("click", closeEditForm);

// ====== UPDATE STUDENT (PUT REQUEST) ======
editForm.addEventListener("submit", async function(event) {
    event.preventDefault();

    // Collect updated student data
    const updatedStudent = {
        name: document.getElementById("editName").value,
        email: document.getElementById("editEmail").value,
        course: document.getElementById("editCourse").value
    };

    try {
        // Send PUT request to update student
        const response = await fetch(
            `http://localhost:8080/api/students/${currentEditingStudentId}`,
            {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(updatedStudent)
            }
        );

        if (!response.ok) {
            throw new Error("Could not update student");
        }

        showMessage("Student updated successfully");
        closeEditForm();

        // Refresh student list
        getAllBtn.click();

    } catch (error) {
        showMessage(`Error: ${error.message}`);
    }
});

// ====== DELETE STUDENT (DELETE REQUEST) ======
async function deleteStudent(id) {
    // Ask for confirmation
    if (!confirm("Are you sure you want to delete this student?")) {
        return;
    }

    try {
        // Send DELETE request to remove student
        const response = await fetch(
            `http://localhost:8080/api/students/${id}`,
            {
                method: "DELETE"
            }
        );

        if (!response.ok) {
            throw new Error("Could not delete student");
        }

        showMessage("Student deleted successfully");

        // Refresh student list
        getAllBtn.click();

    } catch (error) {
        showMessage(`Error: ${error.message}`);
    }
}

// ====== SHOW MESSAGE FUNCTION ======
function showMessage(text) {
    message.textContent = text;
    
    // Auto-hide message after 3 seconds
    setTimeout(() => {
        message.textContent = "";
    }, 3000);
}
