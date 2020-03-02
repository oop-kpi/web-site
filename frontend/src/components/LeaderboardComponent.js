import React, {useEffect, useState} from "react";
import { makeStyles } from "@material-ui/core/styles";
import {Card, CardHeader, Container, Modal, TextField} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import axios from "axios";
import {API_URL} from "../constants/ApiConstants";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";
import Paper from "@material-ui/core/Paper";


const useStyles = makeStyles({
    table: {
    },
});
const LeaderboardComponent = () => {
    const classes = useStyles();
    const [resp,setResp] = useState([])
    function fetchData() {
        axios.get(API_URL+"leaderboard").then(resp =>setResp(resp.data))
    }

    useEffect(() => {
        fetchData();
    }, []);
    return (

        <Container maxWidth="md" >
            <Card raised="true" style={{margin:"10px"}}>
                <Typography variant="h4" style={{color:"#60394A",padding:"0 50px"}}>
                    Список кращих студентів:
                </Typography>
            <TableContainer component={Paper}>
                <Table className={classes.table}  aria-label="a dense table">
                    <TableHead>
                        <TableRow>
                            <TableCell style={{fontSize:20}}>ПІБ</TableCell>
                            <TableCell style={{fontSize:20}} align="right">Група</TableCell>
                            <TableCell style={{fontSize:20}} align="right">Здано лабораторних</TableCell>
                            <TableCell style={{fontSize:20}} align="right">Загальний бал</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {resp.map(row => (
                            <TableRow key={row.name}>
                                <TableCell component="th" scope="row">
                                    {row.name}
                                </TableCell>
                                <TableCell align="right">{row.group}</TableCell>
                                <TableCell align="right">{row.labsCount}</TableCell>
                                <TableCell align="right">{row.mark}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            </Card>
        </Container>
    );
};

export default LeaderboardComponent;
