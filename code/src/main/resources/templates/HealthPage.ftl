<#setting time_zone="US/Central">

<#macro healthClass health>
    <#if health == 'HEALTHY'>
    class="healthy"
    <#elseif health == 'DEGRADED'>
    class="degraded"
    <#elseif health == 'NEEDS_ATTENTION'>
    class="attention"
    <#elseif health == 'BROKEN'>
    class="broken"
    </#if>
</#macro>

<!doctype html>
<html>
<head>
    <meta name="viewport" content="width=device-width, maximum-scale=1, minimum-scale=1, user-scalable=no"/>
    <style>
        .statKey {
            padding-left: 10px;
        }
        .healthy {
            color: white;
            background-color: green;
        }
        .degraded {
            color: black;
            background-color: yellow;
        }
        .attention {
            color: black;
            background-color: deeppink;
        }
        .broken {
            color: black;
            background-color: red;
        }
        table {
            width: 650px;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
    </style>
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.2.3.min.js"></script>

    <script type="text/javascript">

        $(document).ready(function(){

            $(function() {
                $("tr[class=statRow]").find("td").hide();
                $("table").click(function(event) {
                    event.stopPropagation();
                    var $target = $(event.target);
                    if ( $target.closest("tr").attr("class") == "statRow" ) {
                        $target.slideUp();
                        $target.closest("tr").prev().find("td:first").html("+");
                    } else {
                        $target.closest("tr").next().find("td").slideToggle();
                        if ($target.closest("tr").find("td:first").html() == "+")
                            $target.closest("tr").find("td:first").html("-");
                        else
                            $target.closest("tr").find("td:first").html("+");
                    }
                });
            });

        });

    </script>
</head>
<body>
<h1>${applicationId}</h1>
<h2>Version ${applicationVersion}</h2>
<h2>Deployment Date ${applicationDeploymentDate}</h2>
<h3>${overallHealth.getDisplayName()} as of ${creationTime?datetime}<form action="health" method="post"><input type="submit" value="Refresh Health"></form></h3>

<table>
<#list healthResourcesList as resource>
    <tr <@healthClass resource.healthEnum />><td>+</td><td>${resource.name}: ${resource.description}</td></tr>
    <tr class="statRow">
        <td></td>
        <td>
            <table>
                <#list resource.stats as stat>
                    <tr>
                        <td class="statKey">${stat.key}</td>
                        <td class="statVal">${stat.value?replace("\n", "<br/>")?replace("\t","&nbsp;&nbsp;")}</td>
                    </tr>
                </#list>
            </table>
        </td>
    </tr>
</#list>
</table>
</body>
</html>
