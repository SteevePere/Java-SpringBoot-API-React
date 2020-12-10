import React from 'react';
import { BrowserRouter, Route, Switch }
	from 'react-router-dom';

import AuthenticatedRoute from '../AuthenticatedRoute/AuthenticatedRoute';
import AdminRoute from '../AdminRoute/AdminRoute';
import NotFound from '../NotFound/NotFound';

import Login from 'Components/Auth/Login/Login';
import Register from 'Components/Auth/Register/Register';
import Profile from 'Components/Views/Profile/Profile';
import Users from 'Components/Views/Users/Users';
import Boards from 'Components/Views/Boards/Boards';
import KanbanBoard from 'Components/Views/Boards/KanbanBoard/KanbanBoard';
import Dashboard from 'Components/Views/Dashboard/Dashboard';


const Router = () =>
{
	return (

		<BrowserRouter>
			<Switch>
				<Route path='/' exact component={Login}/>
				<Route path='/login' exact component={Login}/>
				<Route path='/register' exact component={Register}/>
				<AuthenticatedRoute exact path='/profile' component={Profile}/>
				<AuthenticatedRoute exact path='/users' component={Users}/>
				<AuthenticatedRoute exact path='/boards' component={Boards}/>
				<AuthenticatedRoute exact path='/boards/:id' component={KanbanBoard}/>
				<AdminRoute exact path='/dashboard' component={Dashboard}/>
				<Route component={NotFound}/>
			</Switch>
		</BrowserRouter>
	);
}


export default Router;
