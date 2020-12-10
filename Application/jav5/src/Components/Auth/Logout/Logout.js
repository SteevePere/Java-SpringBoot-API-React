import React from 'react';
import { NavLink } from 'react-router-dom';

import { auth } from 'Lib/auth';


const Logout = (props) =>
{

	const signOut = () =>
	{
		auth.signOut(props);
	}


	return (

		<NavLink
			to='#'
			onClick={signOut}
		>
			Sign Out
		</NavLink>
	);
}


export default Logout;
