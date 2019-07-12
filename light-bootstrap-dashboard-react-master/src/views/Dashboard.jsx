import React, { Component } from "react";
import ChartistGraph from "react-chartist";
import { Grid, Row, Col } from "react-bootstrap";

import { Card } from "components/Card/Card.jsx";	
import { TableList} from "components/Table/TableList.jsx";
import { FormInputs } from "components/FormInputs/FormInputs.jsx";
import {
  optionsSales,
  responsiveSales,
  } from "variables/Variables.jsx";

class Dashboard extends Component {
  constructor(props) {
    super(props);

    this.refreshPage = this.refreshPage.bind(this)
    this.handleChange = this.handleChange.bind(this);

    this.state = {
      error: null,
      isLoaded: false,
      deviceName :'',
      severity :'',
      dPie: {
        labels:[],
        series:[],
        legends:{
          names:[],
          types: ["info", "danger", "warning"]
        }
      },
      lineChart: {
        dataSales:{
          labels :[],
          series: [],
          legendSales : {
            names: [],
            types: ["info", "danger", "warning"]
          }
      }
    },
    items: [] 
    };
  }
  componentDidMount() {
    this.fetchMessageStatistics();
    this.fetchDeviceStatistics();
    this.fetchMessageList();
  }

  fetchMessageList(deviceParam, severityParam){
    let dParam = deviceParam;
    if(dParam==undefined)
       dParam ='';
    let sParam = severityParam;
    if(sParam == undefined)
       sParam = '';
    fetch("http://localhost:5000/messages?deviceName="+dParam+"&severity="+sParam)
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            isLoaded: true,
            items: result
          });
          
        },
        // Note: it's important to handle errors here
        // instead of a catch() block so that we don't swallow
        // exceptions from actual bugs in components.
        (error) => {
          this.setState({
            isLoaded: true,
            error
          });
        }
      )

  }

  fetchDeviceStatistics(deviceParam){
    let deviceName = deviceParam;
    if(deviceName==undefined)
       deviceName=''
    fetch("http://localhost:5000/device/stats?deviceName="+deviceName)
      .then(res => res.json())
      .then(
        (result) => {
          var legends=[]; var labels=[]; var series=new Array();var count=1;
          Object.keys(result).forEach(function(key) {
            var obj = result[key];
            legends.push(obj.deviceName);
            var messages = obj.messageCount;
            var dataSeries = new Array();
            Object.keys(messages).forEach(function(key1) {
              
              if(count==1){
                labels.push(key1);
              }
              dataSeries.push(messages[key1]);
              
            });
            count++;  
            series.push(dataSeries);
          });
          
          
          this.setState({
            isLoaded: true,
            lineChart: {
              dataSales:{
                labels :labels,
                series: series,
                legendSales :{
                  names: legends,
                  types: ["info", "danger", "warning"]
                }
            }
          }
        });
          
        },
        // Note: it's important to handle errors here
        // instead of a catch() block so that we don't swallow
        // exceptions from actual bugs in components.
        (error) => {
          this.setState({
            isLoaded: true,
            error
          });
        }
      )
  }

  fetchMessageStatistics(deviceParam, sevParam){
    let dParam = deviceParam; let sParam = sevParam;
    if(dParam == undefined)
       dParam = '';
    if(sParam == undefined)
       sParam = '';

    fetch("http://localhost:5000/message/stats?deviceName="+dParam+"&severity="+sParam)
      .then(res => res.json())
      .then(
        (result) => {
          var l = [];
          Object.keys(result).forEach(function(key) {
            l.push(result[key]);
          });
          
          var l1 =[]; var s1=[];
          Object.keys(l).forEach(function(key) {
            var t =l[key];
            l1.push("Sev "+t.severity); s1.push(t.messageCount);
          });
          
          this.setState({
            isLoaded: true,
            dPie: {
              labels:s1,
              series:s1,
              legends:{
                names:l1,
                types: ["info", "danger", "warning"]  
              }
            }
          });
          
        },
        // Note: it's important to handle errors here
        // instead of a catch() block so that we don't swallow
        // exceptions from actual bugs in components.
        (error) => {
          this.setState({
            isLoaded: true,
            error
          });
        }
      )
  }
  createLegend(json) {
    var legend = [];
    for (var i = 0; i < json["names"].length; i++) {
      var type = "fa fa-circle text-" + json["types"][i];
      legend.push(<i className={type} key={i} />);
      legend.push(" ");
      legend.push(json["names"][i]);
    }
    return legend;
  }

  refreshPage(){
    this.fetchMessageStatistics(this.state.deviceName, this.state.severity);
    this.fetchDeviceStatistics(this.state.deviceName);
    this.fetchMessageList(this.state.deviceName, this.state.severity);
  }

  handleChange(event) {
    if(event.target.id=="deviceId")
       this.setState({deviceName: event.target.value});
    else
       this.setState({severity: event.target.value});
  }

  render() {
    return (
      <div className="content">
        <Grid fluid>
		  <Row>
		            <FormInputs
                      ncols={["col-md-2", "col-md-2", "col-md-2"]}
                      properties={[
                        {
                          label: "Device",
                          type: "text",
                          bsClass: "form-control",
                          placeholder: "Device",
                          id: "deviceId",
                          value: this.state.deviceName,
                          onChange : this.handleChange
                        },
                        {
                          label: "Severity",
                          type: "text",
                          bsClass: "form-control",
                          placeholder: "Severity",
                          id: "severityId",
                          value: this.state.severity,
                          onChange : this.handleChange
                        },
						            {    
                          label: "",
                          type: "button",
                          bsClass: "form-control",
                          placeholder: "Search",
                          defaultValue: "Search",
                          onClick:this.refreshPage
                        }
                      ]}
                    />
                    
                    
          </Row>
          <Row>
            <Col md={8}>
              <Card
                statsIcon="fa fa-history"
                id="chartHours"
                title="Device Behavior"
                stats="Updated 3 minutes ago"
                content={
                  <div className="ct-chart">
                    <ChartistGraph
                      data={this.state.lineChart.dataSales}
                      type="Line"
                      options={optionsSales}
                      responsiveOptions={responsiveSales}
                    />
                  </div>
                }
                legend={
                  <div className="legend">{this.createLegend(this.state.lineChart.dataSales.legendSales)}</div>
                }
              />
            </Col>
            <Col md={4}>
              <Card
                statsIcon="fa fa-clock-o"
                title="Message Statistics"
                
                stats="Updated at 5th May 2019"
                content={
                  <div
                    id="chartPreferences"
                    className="ct-chart ct-perfect-fourth"
                  >
                    <ChartistGraph data={this.state.dPie} type="Pie" />
                  </div>
                }
                legend={
                  <div className="legend">{this.createLegend(this.state.dPie.legends)}</div>
                }
              />
            </Col>
          </Row>
		  <Row>
		     <TableList items={this.state.items}/>
		  </Row>

          
        </Grid>
      </div>
    );
  }
}

export default Dashboard;
