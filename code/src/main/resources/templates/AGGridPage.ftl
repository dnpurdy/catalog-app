<!DOCTYPE html>
<html>
<head>
    <link href="/style.css" rel="stylesheet"/>
    <script src="https://unpkg.com/ag-grid/dist/ag-grid.min.noStyle.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/ag-grid/dist/styles/ag-grid.css">
    <link rel="stylesheet" href="https://unpkg.com/ag-grid/dist/styles/ag-theme-balham.css">
</head>
<body>

<div >
    <!-- Give the grid a default size of 100% x 100%. The buttons then change this
         style as the user selects. -->
    <div id="myGrid" class="ag-theme-balham" style="height: 700px;"></div>
</div>

<script type="text/javascript" charset="utf-8">
    // specify the columns
    ${agColumns}

    // let the grid know which columns and what data to use
    var gridOptions = {
        columnDefs: columnDefs,
        enableSorting: true,
        enableFilter: true
    };

    function dateFormatter(params) {
        console.log(params);
        var dateobj = new Date(params.value);
        var mm = dateobj.getMonth() + 1; // getMonth() is zero-based
        var dd = dateobj.getDate();

        return [dateobj.getFullYear(), (mm>9 ? '' : '0') + mm, (dd>9 ? '' : '0') + dd].join('-');
    }

    function currencyFormatter(params) {
        return '$' + formatNumber(params.value, 2, true);
    }

    function percentFormatter(params) {
        return formatNumber(params.value*100, 6, false)+'%';
    }

    function formatNumber(number, fixed, comma) {
        // this puts commas into the number eg 1000 goes to 1,000,
        // i pulled this from stack overflow, i have no idea how it works
        if (comma) {
            return Number(number).toFixed(fixed).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        } else {
            return Number(number).toFixed(fixed).toString()
        }
    }

    // lookup the container we want the Grid to use
    var eGridDiv = document.querySelector('#myGrid');

    // create the grid passing in the div to use together with the columns & data we want to use
    new agGrid.Grid(eGridDiv, gridOptions);

    fetch('${dataUri}').then(function(response) {
        return response.json();
    }).then(function(data) {
        gridOptions.api.setRowData(data);
    })

</script>
</body>
</html>