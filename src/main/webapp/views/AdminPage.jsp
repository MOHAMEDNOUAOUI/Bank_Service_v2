<%@ page import="java.util.List" %>
<%@ page import="com.wora.bankservice.entity.Demande" %>
<%@ page import="com.wora.bankservice.entity.DemandeStatut" %>
<%@ page import="com.wora.bankservice.entity.Statut" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%List<Demande> DemandeList = (List<Demande>) request.getAttribute("DemandeList");%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/cssfordemand.css">
    <style>
        .popup {
            display: none;
            position: fixed;
            left: 50%;
            top: 40%;
            transform: translate(-50%, -50%);
            background-color: white;
            padding: 20px;
            border: 1px solid #ccc;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            z-index: 1000;
            width: 50vw;
            height: 50vh;
            border-radius: 20px;
            justify-content: right;
            align-items: center;
        }

        .popup .minipopup {
            width: 23rem;
            height: 23rem;
            position: absolute;
            top: 50%;
            left: 10%;
            transform: translate(-50%, -50%);
            border-radius: 20px;
            background: radial-gradient(circle at 12.3% 19.3%, rgb(85, 88, 218) 0%, rgb(95, 209, 249) 100.2%);
            box-shadow: rgba(2, 140, 150, 0.5) 0px 8px 30px; /* Using the same color */
        }

        .popup-content {
            width: 65%;
            height: 60%;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }

        .popuptopper {
            display: flex;
            justify-content: space-between;
        }

        .close-btn {
            position: absolute;
            top: 1rem;
            right: 1rem;
            cursor: pointer;
            font-size: 2rem;
        }

        tr {
            cursor: pointer;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        .container-next {
            display: flex;
            width: 100%;
            align-content: center;
            justify-content: space-between;
        }

        #popupContent {
            display: flex;
            flex-direction: column;
            gap: 1rem;
            transition: transform 0.5s ease-in-out, opacity 0.5s ease-in-out;
            transform: translateX(0);
            opacity: 1;
        }

        #popupContent.slide-out {
            transform: translateX(10%);
            opacity: 0;
        }

        #popupContent #dateandname {
            font-size: 1rem;
            font-weight: bold;
        }

        #popupContent #dateandname span {
            color: grey;
            font-size: 1rem;
        }

        #popupContent h4 {
            font-weight: bold;
            font-size: 2rem;
        }

        #popupContent div p {
            font-size: 1.2rem;
            color: #505050;
        }

        #popupContent div p strong {
            color: rgb(2, 140, 150);
            font-weight: bold;
        }

        #popupContent div p span {
            font-size: 1.1rem;
            color: black;
            font-weight: bold;
        }

        #popupContent button {
            width: fit-content;
            padding: 1rem 3rem;
            border-radius: 40px;
            background: radial-gradient(circle at 12.3% 19.3%, rgb(85, 88, 218) 0%, rgb(95, 209, 249) 100.2%);
            color: white;
            cursor: pointer;
            border: none;
            box-shadow: rgba(2, 140, 150, 0.5) 0px 3px;
            font-weight: bold;
            font-size: 1.1rem;
        }

        #popupContent ul {
            height: 14rem;
            gap: 0.5rem;
            display: flex;
            flex-direction: column;
            overflow-y: auto;
        }
    </style>
    <title>Demande List</title>
</head>
<body>
<div class="container">
    <h1>Demande List</h1>
    <div class="container-next">
        <input pattern="yyyy-mm-dd" type="date" id="date-filter" placeholder="Filter">
        <select name="statut">
            <option selected value="0">ALL</option>
            <option value="1">ACCEPTED</option>
            <option value="2">CANCELED</option>
            <option value="3">REFUSED</option>
            <option value="4">PENDING</option>
        </select>
    </div>
    <%
        if (DemandeList != null && !DemandeList.isEmpty()) {
    %>
    <table id="demandeTable">
        <thead>
        <tr>
            <th>Nom</th>
            <th>Prenom</th>
            <th>Telephone</th>
            <th>Date de naissance</th>
            <th>Date dembauche</th>
            <th>CIN</th>
            <th>Email</th>
            <!-- Add more table headers as needed -->
        </tr>
        </thead>
        <tbody>
        <% for (Demande d : DemandeList) {
            List<DemandeStatut> statusList = new ArrayList<>(d.getDemandeStatuts());
            DemandeStatut lastStatut = statusList.get(statusList.size() - 1);
        %>
        <tr data-statut="<%= lastStatut.getStatut().getId()%>" onclick="callpopup(<%= d.getId() %>)">
            <td><%= d.getNom() %>
            </td>
            <td><%= d.getPrenom() %>
            </td>
            <td><%= d.getTelephone() %>
            </td>
            <td><%= d.getDatedebauche() %>
            </td>
            <td><%= d.getDatedebauche() %>
            </td>
            <td><%= d.getCIN() %>
            </td>
            <td><%= d.getEmail() %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <p class="empty-message">No demandes found.</p>
    <% } %>
</div>

<div id="popup" class="popup">
    <div class="minipopup"></div>
    <div class="popup-content">
        <span class="close-btn" onclick="closePopup()">&times;</span>
        <div id="popupContent">

        </div>
    </div>
</div>

<script>
    const olddata = document.querySelector('#demandeTable tbody').innerHTML;
    const alltr = document.querySelectorAll('#demandeTable tbody tr');
    var popupdata = null;

    const demandeData = {
        <% for (Demande d : DemandeList) { %>
        "<%= d.getId() %>": {
            id: <%= d.getId() %>,
            nom: "<%= d.getNom() %>",
            prenom: "<%= d.getPrenom() %>",
            telephone: "<%= d.getTelephone() %>",
            datedenaissance: "<%= d.getDatenaissance() %>",
            datedebauche: "<%= d.getDatedebauche() %>",
            cin: "<%= d.getCIN() %>",
            email: "<%= d.getEmail() %>",
            montant: "<%= d.getMontant() %>",
            civilite: "<%= d.getCivilite() %>",
            projectType: "<%= d.getMonproject() %>",
            type: "<%= d.getJesuis() %>",
            duree: "<%= d.getDuree() %>",
            mensualite: "<%= d.getMensualite() %>",
            totalRevenue: "<%= d.getTotalrevenue() %>",
            demandeStatutList: [
                <%
                Set<DemandeStatut> statuses = d.getDemandeStatuts();
                List<DemandeStatut> statusList = new ArrayList<>(statuses);
                for (int i = 0; i < statusList.size(); i++) {
                    DemandeStatut demandeStatut = statusList.get(i);
                %>
                {
                    statut: "<%= demandeStatut.getStatut().getStatut() %>",
                    dateInsert: "<%= demandeStatut.getDateInsert() %>"
                }<% if (i < statuses.size() - 1) { %>, <% } %>
                <%
                }
                %>
            ]
        }<% if (!d.equals(DemandeList.get(DemandeList.size() - 1))) { %>, <% } %>
        <% } %>
    };

    const demandeDatawithDate = {};
    <% for (Demande d : DemandeList) { %>
    var dateKey = "<%= d.getDatedebauche() %>";
    if (!demandeDatawithDate[dateKey]) {
        demandeDatawithDate[dateKey] = [];
    }
    demandeDatawithDate[dateKey].push({
        id: <%= d.getId() %>,
        nom: "<%= d.getNom() %>",
        prenom: "<%= d.getPrenom() %>",
        telephone: "<%= d.getTelephone() %>",
        datedenaissance: "<%= d.getDatenaissance() %>",
        datedebauche: "<%= d.getDatedebauche() %>",
        cin: "<%= d.getCIN() %>",
        email: "<%= d.getEmail() %>",
        montant: "<%= d.getMontant() %>",
        civilite: "<%= d.getCivilite() %>",
        projectType: "<%= d.getMonproject() %>",
        type: "<%= d.getJesuis() %>",
        duree: "<%= d.getDuree() %>",
        mensualite: "<%= d.getMensualite() %>",
        totalRevenue: "<%= d.getTotalrevenue() %>",
        demandeStatutList: [
            <%
            Set<DemandeStatut> statuses = d.getDemandeStatuts();
            List<DemandeStatut> statusList = new ArrayList<>(statuses);
            for (int i = 0; i < statusList.size(); i++) {
                DemandeStatut demandeStatut = statusList.get(i);
            %>
            {
                statut: "<%= demandeStatut.getStatut().getStatut() %>",
                statutid: "<%= demandeStatut.getStatut().getId()%>",
                dateInsert: "<%= demandeStatut.getDateInsert() %>"
            }<% if (i < statuses.size() - 1) { %>, <% } %>
            <%
            }
            %>
        ]
    });
    <% } %>

    function getmoredata(id) {
        const demande = demandeData[id];
        if (demande) {
            var statusDetails = "";
            var content =
                "<p id='dateandname'>" + demande.nom + ", " + demande.prenom + " <span>" + formatDate(demande.datedebauche) + "</span></p>" +
                "<h4>" + demande.projectType + "</h4>" +
                "<div>" +
                "<p><span>Description:</span> The ID of the demand is <strong>" + demande.id + "</strong>, " +
                "the type of this demand is <strong>" + demande.type + "</strong>, " +
                "and the duration is <strong>" + demande.duree + "</strong>. " +
                "Here are more details: " +
                "<strong>Téléphone:</strong> " + demande.telephone + ", " +
                "<strong>Date de naissance:</strong> " + formatDate(demande.datedenaissance) + ", " +
                "<strong>CIN:</strong> " + demande.cin + ", " +
                "<strong>Email:</strong> " + demande.email + ", " +
                "<strong>Montant:</strong> " + demande.montant + ", " +
                "<strong>Civilité:</strong> " + demande.civilite + ", " +
                "<strong>Mensualité:</strong> " + demande.mensualite + ", " +
                "<strong>Statuts:</strong> <br>" + statusDetails +
                "<strong>Revenu total:</strong> " + demande.totalRevenue + ".</p>" +
                "</div>" +
                "<button onclick='findhistory(" + id + ")'>Historique</button>";
            document.getElementById('popupContent').innerHTML = content;
            console.log(demande.nom);
            document.getElementById('popup').style.display = 'flex';
        }
    }


    function callpopup(id) {
        console.log(id);
        getmoredata(id);
    }

    function closePopup() {
        document.querySelector('#popup').style.display = 'none';
    }


    document.querySelector('#date-filter').addEventListener('input', function () {
        console.log(this.value);
        var demandelist = demandeDatawithDate[this.value];
        document.querySelector('#demandeTable tbody').innerHTML = '';
        if (demandelist && demandelist.length > 0) {
            demandelist.forEach(demand => {
                var laststatutid = demand.demandeStatutList[demand.demandeStatutList.length - 1].statutid;
                CreateDemandInsideHtml(demand, laststatutid);
            })
        }
    })


    function CreateDemandInsideHtml(demand, laststatutid) {
        const container = document.querySelector('#demandeTable tbody');
        const row = document.createElement('tr');
        row.setAttribute('onclick', "callpopup(" + demand.id + ")");
        row.setAttribute('data-statut', laststatutid);
        var content = "<td>" + demand.nom + "</td>" +
            "<td>" + demand.prenom + "</td>" +
            "<td>" + demand.telephone + "</td>" +
            "<td>" + demand.datedenaissance + "</td>" +
            "<td>" + demand.datedebauche + "</td>" +
            "<td>" + demand.cin + "</td>" +
            "<td>" + demand.email + "</td>";
        row.innerHTML = content;
        container.appendChild(row);
    }

    function formatDate(dateString) {
        const options = {year: 'numeric', month: 'long', day: 'numeric'};
        const date = new Date(dateString);
        return date.toLocaleDateString('en-US', options);
    }


    function findhistory(id) {
        const demande = demandeData[id];
        const popupContent = document.getElementById('popupContent');
        popupContent.classList.add('slide-out');
        popupdata = popupContent.innerHTML;

        setTimeout(() => {
            let historyContent = "<ul>";
            demande.demandeStatutList.forEach(demandestatut => {
                historyContent += "<li><strong>Statut:</strong> " + demandestatut.statut +
                    ", <strong>Date:</strong> " + formatDate(demandestatut.dateInsert) + "</li>";
            });
            historyContent += "</ul>";
            historyContent += "<button onclick='goback()'>Go back</button>";
            popupContent.innerHTML = historyContent;
            popupContent.classList.remove('slide-out');
        }, 500);
    }

    function goback() {
        const popupContent = document.getElementById('popupContent');
        popupContent.classList.add('slide-out');
        setTimeout(() => {
            popupContent.innerHTML = popupdata;
            popupContent.classList.remove('slide-out');
        }, 500);
    }


    document.querySelector('select').addEventListener('change', function () {
        const selectedStatut = this.value;
        console.log(selectedStatut)
        const rows = document.querySelectorAll('tbody tr');
        rows.forEach(row => {
            const statutValue = row.getAttribute('data-statut');
            if (selectedStatut == 0 || statutValue == selectedStatut) {
                console.log(statutValue);
                row.style.display = '';
            } else {
                row.style.display = 'none';
            }
        });
    });

</script>
</body>
</html>