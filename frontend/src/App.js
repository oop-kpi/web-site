import React, {useEffect, useState} from 'react';
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
import LectureUploadingPage from "./components/LectureUploadingPage";
import CurrentUserPage from "./components/CurrentUserPage";
import UploadLabComponent from "./components/UploadLabComponent";
import LabsPoolComponent from "./components/LabsPoolComponent";
import EditLabPage from "./components/EditLabPage";
import LeaderboardComponent from "./components/LeaderboardComponent";
import BrowseUsersComponent from "./components/BrowseUsersComponent";
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
    const [user,setUser] = useState(null)
    const classes = useStyles();
     useEffect(()=>{
          const user = JSON.parse(localStorage.getItem('user'))
         if (user != null) {
             setUser(user)
         }
     },[])
  return (
      <Router>
          <div className={classes.root}>
              <AppBar position="static" >
                  <Toolbar m={-12321} className={classes.root}>
                      <Button className={classes.title} href="/lectures">Лекції</Button>
                      <Button className={classes.title} href="/leaderboard">Рейтинг</Button>
                      {user && (user.roles.includes("ROLE_TEACHER") || user.roles.includes("ROLE_REVIEWER"))&& <Button href="/evaluateLab">Оцінити лабораторні</Button>}
                      {user && (user.roles.includes("ROLE_TEACHER") || user.roles.includes("ROLE_REVIEWER"))&& <Button href="/browse-users">Список учнів</Button>}
                      {user ?<Button href="me">{user.login}</Button> :<Button href="login" color="inherit">Увійти</Button>}
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
            <Route path="/leaderboard">
                <LeaderboardComponent></LeaderboardComponent>
          </Route>
          <Route path="/lectures">
          <LecturesPage></LecturesPage>
          </Route>
            <Route path="/browse-users">
          <BrowseUsersComponent></BrowseUsersComponent>
          </Route>
            <Route path="/uploadLab">
          <UploadLabComponent></UploadLabComponent>
          </Route>
            <Route path="/evaluateLab">
          <LabsPoolComponent></LabsPoolComponent>
          </Route>
          <Route path="/login">
            <Login></Login>
          </Route>
            <Route path="/register">
            <RegisterPage></RegisterPage>
          </Route>
            <Route path="/me">
            <CurrentUserPage></CurrentUserPage>
            </Route>
            <Route path="/uploadLecture">
            <LectureUploadingPage></LectureUploadingPage>
          </Route>
        </Switch>
      </Router>
  );
}

export default App;
