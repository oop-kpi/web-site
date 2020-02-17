import React from 'react';
import logo from './logo.svg';
import './App.css';
import Login from "./LoginPage";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import AppBar from "material-ui/AppBar";
import MuiThemeProvider from "material-ui/styles/MuiThemeProvider";

function App() {
  return (
      <Router>                <MuiThemeProvider>
        <AppBar
          title="ООП"
      >
          <h3>
<Link to="/login">Login</Link>
          </h3>
        </AppBar> </MuiThemeProvider>

        <Switch>
          <Route exact path="/">
            <div>Home</div>
          </Route>
          <Route path="/about">
            <div>About</div>
          </Route>
          <Route path="/dashboard">
            <div>Dashboard</div>
          </Route>
          <Route path="/login">
            <Login></Login>
          </Route>
        </Switch>
      </Router>
  );
}

export default App;
