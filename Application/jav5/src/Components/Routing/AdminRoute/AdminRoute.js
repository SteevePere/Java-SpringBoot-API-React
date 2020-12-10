import React from 'react';
import { Route, Redirect } from 'react-router-dom';

import AdminLayout from 'Components/Layout/AdminLayout/AdminLayout';

import { auth } from 'Lib/auth';


const AdminRoute = ({ component: Component, ...rest }) =>
{
    return (

        <Route
            {...rest}
            render={props =>
                auth.isAdmin() ? (
					<AdminLayout
						{...props}
						content={<Component {...props}/>}
					/>
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


export default AdminRoute;
