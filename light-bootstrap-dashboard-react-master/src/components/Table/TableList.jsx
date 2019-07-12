import React, { Component } from "react";
import { Grid, Row, Col, Table } from "react-bootstrap";

import Card from "components/Card/Card.jsx";
import { thArray} from "variables/Variables.jsx";

export class TableList extends Component {
  constructor(props) {
    super(props);
    
  }
  
  render() {
    return (
      <div className="content">
        <Grid fluid>
          <Row>
            <Col md={12}>
              <Card
                title="Browse messages"
                category="Messages ordered by date and severity"
                ctTableFullWidth
                ctTableResponsive
                content={
                  <Table striped hover>
                    <thead>
                      <tr>
                        {thArray.map((prop, key) => {
                          return <th key={key}>{prop}</th>;
                        })}
                      </tr>
                    </thead>
                    <tbody>
                      {this.props.items.map( (item ,index) => {
                        return (
                          <tr key={index}>
                           <td>{item.deviceName}</td>
                           <td>{item.severity}</td>
                           <td>{item.message}</td>
                           <td>{item.lastUpdatedDate}</td>
                          </tr>
                        );
                      })}
                    </tbody>
                  </Table>
                }
              />
            </Col>

          </Row>
        </Grid>
      </div>
    );
  }
}

export default TableList;
