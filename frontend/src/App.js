import React from 'react';
import './App.css';
import Login from "./components/LoginPage";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import RegisterPage from "./components/RegisterPage";
import LecturesPage from "./components/LecturesPage";

const useStyles = makeStyles(theme => ({
    root: {
        background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
        flexGrow: 1,
        spacing: 8
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
              <AppBar position="static" >
                  <Toolbar m={-12321} className={classes.root}>
                      <Link to="/lectures">Лекції</Link>
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
          <Route path="/lectures">
          <LecturesPage></LecturesPage>
          </Route>
          <Route path="/login">
            <Login></Login>
          </Route>
            <Route path="/register">
            <RegisterPage></RegisterPage>
          </Route>
        </Switch>
      </Router>
  );
}

export default App;
