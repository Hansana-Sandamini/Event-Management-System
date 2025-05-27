<!--    Load All-->
$(document).ready(function () {
    $("#ajax-btn").click(function () {
        $.ajax({
            url: "http://localhost:8080/App1_Web_exploded/event",
            method: "GET",
            success: function (response) {
                console.log(response);
                const tbody = $('#eventTable tbody');
                tbody.empty();

                response.forEach(function(event) {

                    tbody.append('<tr>' +
                        '<td>' + event.eid + '</td>' +
                        '<td>' + event.ename + '</td>' +
                        '<td>' + event.edescription + '</td>' +
                        '<td>' + event.edate + '</td>' +
                        '<td>' + event.eplace + '</td>' +
                        '</tr>'
                    );
                });
            },
            error: function (error) {
                console.log(error);
            }
        });
    });
});

<!--    Save-->
$('.saveBtn').click(function (e) {
    e.preventDefault();
    const event = {
        eid: $('#eventId').val(),
        ename: $('#name').val(),
        edescription: $('#description').val(),
        edate: $('#date').val(),
        eplace: $('#place').val()
    };
    $.ajax({
        url: "http://localhost:8080/App1_Web_exploded/event",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(event),
        success: function () {
            alert("Event Saved Successfully!");
            $("#event-form")[0].reset();
            $.ajax({
                url: "http://localhost:8080/App1_Web_exploded/event",
                method: "GET",
                success: function (response) {
                    console.log(response);
                    const tbody = $('#eventTable tbody');
                    tbody.empty();

                    response.forEach(function(event) {

                        tbody.append('<tr>' +
                            '<td>' + event.eid + '</td>' +
                            '<td>' + event.ename + '</td>' +
                            '<td>' + event.edescription + '</td>' +
                            '<td>' + event.edate + '</td>' +
                            '<td>' + event.eplace + '</td>' +
                            '</tr>'
                        );
                    });
                },
                error: function (error) {
                    console.log(error);
                }
            });
        },
        error: function (error) {
            console.log(error);
            alert("Failed to Save Event!");
        }
    });
});

<!--    Update-->
$('.updateBtn').click(function (e) {
    e.preventDefault();
    const event = {
        eid: $('#eventId').val(),
        ename: $('#name').val(),
        edescription: $('#description').val(),
        edate: $('#date').val(),
        eplace: $('#place').val()
    };
    $.ajax({
        url: "http://localhost:8080/App1_Web_exploded/event",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(event),
        success: function () {
            alert("Event Updated Successfully!");
            $("#event-form")[0].reset();
            reloadEventTable();
        },
        error: function () {
            alert("Failed to Update Event!");
        }
    });
});

<!--    Delete-->
$('.deleteBtn').click(function (e) {
    e.preventDefault();
    const eid = $('#eventId').val();
    if (!eid) {
        alert("Enter ID to delete.");
        return;
    }
    $.ajax({
        url: "http://localhost:8080/App1_Web_exploded/event?eid=" + eid,
        method: "DELETE",
        success: function () {
            alert("Event Deleted Successfully!");
            $("#event-form")[0].reset();
            reloadEventTable();
        },
        error: function () {
            alert("Failed to Delete Event!");
        }
    });
});

<!--    Row selection-->
$(document).ready(function() {
    let selectedRow = null;

    $(document).on("click", "#eventTable tbody tr", function() {
        if (selectedRow) {
            selectedRow.css({
                'background-color': ''
            });
        }

        $(this).css({
            'background-color': '#97da99'
        });

        selectedRow = $(this);

        const cells = $(this).find("td");
        $("#eventId").val(cells.eq(0).text());
        $("#name").val(cells.eq(1).text());
        $("#description").val(cells.eq(2).text());
        $("#date").val(cells.eq(3).text());
        $("#place").val(cells.eq(4).text());
    });

    $("#clearBtn").click(function() {
        if (selectedRow) {
            selectedRow.css({
                'background-color': ''
            });
            selectedRow = null;
        }
    });

    <!--    Refresh Form-->
    $('.refreshBtn').click(function (e) {
        e.preventDefault();

        $("#event-form")[0].reset();

        if (selectedRow) {
            selectedRow.css({
                'background-color': ''
            });
            selectedRow = null;
        }
    });
});

// Reload table
function reloadEventTable() {
    $.ajax({
        url: "http://localhost:8080/App1_Web_exploded/event",
        method: "GET",
        success: function (response) {
            const tbody = $('#eventTable tbody');
            tbody.empty();

            response.forEach(function(event) {
                tbody.append('<tr>' +
                    '<td>' + event.eid + '</td>' +
                    '<td>' + event.ename + '</td>' +
                    '<td>' + event.edescription + '</td>' +
                    '<td>' + event.edate + '</td>' +
                    '<td>' + event.eplace + '</td>' +
                    '</tr>'
                );
            });
        },
        error: function (error) {
            console.log("Failed to load event data:", error);
        }
    });
}
