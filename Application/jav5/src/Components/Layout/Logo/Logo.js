import React from 'react';


const Logo = () =>
{
	return (

		<img
			src={window.location.origin + '/kanban_logo_3.jpg'}
			alt='Logo'
			style={{
				width: '100%',
				objectFit: 'cover',
				padding: 20,
			}}
		/>
	);
}


export default Logo;
