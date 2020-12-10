import React from 'react';


const ProfilePicture = (props) =>
{

	const {
		isAdmin,
	} = props;


	return (

		<img
			src={
				isAdmin ? window.location.origin + '/admin_logo.png' :
				window.location.origin + '/user_logo.png'
			}
			alt={isAdmin? 'Admin Logo' : 'User Logo'}
			style={{
				margin: 'auto',
				maxHeight: 350,
				maxWidth: '100%',
			}}
		/>
	);
}


export default ProfilePicture;
