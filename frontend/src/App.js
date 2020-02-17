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
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import MuiThemeProvider from "@material-ui/core/styles/MuiThemeProvider";
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';

const useStyles = makeStyles(theme => ({
    root: {
        flexGrow: 1,
    },
    menuButton: {
        marginRight: theme.spacing(2),
    },
    title: {
        flexGrow: 1,
    },
}));


function App() {
    const classes = useStyles();
  return (
      <Router>
          <div className={classes.root}>
              <AppBar position="static">
                  <Toolbar >
                      <Typography variant="h6"  className={classes.title}>
                          News
                      </Typography>
                      <Button href="login" color="inherit">Login</Button>
                  </Toolbar>
              </AppBar>
          </div>

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
