function trElems() {
  var tr1 = document.getElementById("dataTable");
  var myData =[];
  for( var i = 1; i < tr1.rows.length; i++) {
  let p1 = {};
  //alert(tr1.rows[i].cells.item(1).innerHTML);
  p1.Nationality = tr1.rows[i].cells.item(3).innerHTML.trim();
  p1.Sex = tr1.rows[i].cells.item(2).innerHTML.trim();
  myData.push(p1);
  };
console.log(myData);
var yourVlSpec = {
    "$schema": "https://vega.github.io/schema/vega-lite/v4.json",
    "data": {
       "values": [],
         "name": "myData"
    },
    "transform": [
      {"calculate": "datum.sex == 2 ? 'Female' : 'Male'", "as": "gender"}
    ],
    "width": 150,
    "height": 500,
    "title": {
      "text": "Nationality",
      "anchor": "middle",
      "fontSize": 24
    },
    "mark": "bar",
    "encoding": {
      "column": {
        "field": "Nationality",
        "type": "ordinal",
        "spacing": 15,
        "title": "",
        "header": {
          "labelAnchor": "start",
          "labelPadding": 5,
          "labelFontSize": 15
        }
      },
      "y": {
        "aggregate": "count",
        "field": "Nationality",
        "type": "nominal",
        "title": "Amount of students"
      },
      "x": {
        "field": "Sex", "type": "nominal",
        "axis": {"title": ""}
      },
      "color": {
        "field": "Sex", "type": "nominal",
        "scale": {"range": ["#675193", "#ca8861"]}
      }
    },
    "config": {
      "view": {"stroke": "transparent"},
      "axis": {"domainWidth": 1,
        "titleFontSize":14,
        "labelFontSize":15}
    }
  };

vegaEmbed("#vis", yourVlSpec)
 .then((res) => res.view
    .renderer('svg')
    .insert("myData",  myData)
    .run()
    );
}