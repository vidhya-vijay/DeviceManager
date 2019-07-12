import Dashboard from "views/Dashboard.jsx";
import TableList from "views/TableList.jsx";
import Notifications from "views/Notifications.jsx";


const dashboardRoutes = [
  {
    path: "/dashboard",
    name: "Live",
    icon: "pe-7s-graph",
    component: Dashboard,
    layout: "/admin"
  }
];

export default dashboardRoutes;
