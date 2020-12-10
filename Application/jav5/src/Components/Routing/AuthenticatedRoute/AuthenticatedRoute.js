import React from 'react';
import { Route, Redirect } from 'react-router-dom';

import AdminLayout from 'Components/Layout/AdminLayout/AdminLayout';
import UserLayout from 'Components/Layout/UserLayout/UserLayout';

import { auth } from 'Lib/auth';


const AuthenticatedRoute = ({ component: Component, ...rest }) =>
{
    return (

        <Route
            {...rest}
            render={props =>
                auth.isAuthenticated() ? ( auth.isAdmin() ? (
					<AdminLayout
						{...props}
						content={<Component {...props}/>}
					/> ) : (
					<UserLayout
						{...props}
						content={<Component {...props}/>}
					/> )
                ) : (
                    <Redirect
                        to={{
                            pathname: '/login',
                            state: { from: props.location }
                        }}
                    />
                )
            }
        />
    );
}


export default AuthenticatedRoute;
