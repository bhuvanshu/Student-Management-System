const studentForm = document.getElementById("studentForm");
const message = document.getElementById("message");

studentForm.addEventListener("submit", async function (event) {

    event.preventDefault();

    const student = {
        name: document.getElementById("name").value,
        email: document.getElementById("email").value,
        course: document.getElementById("course").value
    };

    try {
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

        message.textContent =
            `Student added successfully with ID: ${savedStudent.id}`;

        studentForm.reset();

    } catch (error) {
        message.textContent = error.message;
    }
});