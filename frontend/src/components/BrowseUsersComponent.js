import React, {forwardRef, useEffect, useState} from 'react';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import AddBox from '@material-ui/icons/AddBox';
import ArrowDownward from '@material-ui/icons/ArrowDownward';
import Check from '@material-ui/icons/Check';
import ChevronLeft from '@material-ui/icons/ChevronLeft';
import ChevronRight from '@material-ui/icons/ChevronRight';
import Clear from '@material-ui/icons/Clear';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import Edit from '@material-ui/icons/Edit';
import FilterList from '@material-ui/icons/FilterList';
import FirstPage from '@material-ui/icons/FirstPage';
import LastPage from '@material-ui/icons/LastPage';
import Remove from '@material-ui/icons/Remove';
import SaveAlt from '@material-ui/icons/SaveAlt';
import Search from '@material-ui/icons/Search';
import ViewColumn from '@material-ui/icons/ViewColumn';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import axios from "axios";
import {API_URL} from "../constants/ApiConstants";
import { useHistory } from "react-router-dom";
import LaunchIcon from '@material-ui/icons/Launch';
import MaterialTable from "material-table";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import TableContainer from "@material-ui/core/TableContainer";


const useStyles = makeStyles(theme => ({
    icon: {
        marginRight: theme.spacing(2),
    },
    heroContent: {
        backgroundColor: theme.palette.background.paper,
        padding: theme.spacing(8, 0, 6),
    },
    heroButtons: {
        marginTop: theme.spacing(4),
    },
    cardGrid: {
        paddingTop: theme.spacing(8),
        paddingBottom: theme.spacing(8),
    },
    modal: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    card: {
        height: '100%',
        display: 'flex',
        flexDirection: 'column',
    },
    cardMedia: {
        paddingTop: '56.25%', // 16:9
    },
    cardContent: {
        flexGrow: 1,
    },
    paper: {
        backgroundColor: theme.palette.background.paper,
        border: '2px solid #000',
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
    },
    footer: {
        backgroundColor: theme.palette.background.paper,
        padding: theme.spacing(6),
    },
}));

const tableIcons = {
    Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
    Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
    Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
    Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
    DetailPanel: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
    Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
    Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
    Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
    FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
    LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
    NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
    PreviousPage: forwardRef((props, ref) => <ChevronLeft {...props} ref={ref} />),
    ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
    Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
    SortArrow: forwardRef((props, ref) => <ArrowDownward {...props} ref={ref} />),
    ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
    ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />)
};



export default function BrowseUsersComponent() {
    const [selectedLab, setSelectedLecture] = useState(null);
    const [users,setUsers] = useState([])
    let history = useHistory();

    const fetchData =  () => {
        axios.get(
            API_URL+'user/getAll',{headers: {'Authorization': 'Bearer '+localStorage.getItem('token')}}).then(resp => setUsers(resp.data)).catch(err => history.push({
                pathname: '/login',
                search: '?err=401',
            })
        )};
    useEffect(() => {
        fetchData();
    }, []);
    const classes = useStyles();

    const columns = [{title:'ПІБ',field:'name'},{title:'Логін',field:'login'},{title:'Пошта',filed:'email'},{title:'Група',field:'group'},{title:'Загальний бал',field:'ball'}]
    const labsColumn = [{title:'Назва',field:'name'},{title:'Оцінка',filed:'mark'}]
    return (
        <Container maxWidth="md" >
            <Card raised="true" style={{margin:"10px"}}>
                <MaterialTable
                    detailPanel={rowData => {
                        if (rowData.laboratoryWorks != null && rowData.laboratoryWorks.length == 0){ return (<div align="center"><h4>Студент не завантажував лабораторні роботи!</h4></div>)}
                        return (
                            <Table className={classes.table} size="small"  >
                                <TableHead>
                                    <TableRow>
                                        <TableCell style={{fontSize:25,fontWeight: 'bold'}} align={"left"}>Назва</TableCell>
                                        <TableCell style={{fontSize:25,fontWeight: 'bold'}}>Бал</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {rowData.laboratoryWorks.map(row => (
                                        <TableRow key={row.name}>
                                            <TableCell style={{fontSize:20}} component="th" scope="row">
                                                {row.name}
                                            </TableCell>
                                            <TableCell style={row.mark>0? {fontSize:18,color:'green'}:{fontSize:18,color:'red'}}>{row.mark}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        )
                    }}
                     actions = {
                        [
                            {
                                icon: LaunchIcon,
                                tooltip: 'Подивитися інформацію про користувача',
                                onClick: (event, rowData) => history.push('/user/'+rowData.login)
                            }]}
                     icons={tableIcons} title="Список студентів" columns={columns} data={users}/>
            </Card>
        </Container>
    );
}