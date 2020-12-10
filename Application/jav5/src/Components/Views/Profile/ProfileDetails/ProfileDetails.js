import React from 'react';
import { Tag, Space, Divider, Typography } from 'antd';

import { RolesModel } from 'Models/RolesModel';

const { Title } = Typography;


const ProfileDetails = ({ user }) =>
{

	const rolesDisplay = {
		[RolesModel.ADMIN]: {
			name: 'ADMIN',
			color: 'red',
		},
		[RolesModel.USER]: {
			name: 'USER',
			color: 'green',
		},
	};


	return (

		<>
			<Title>Hello, {user.sub} !</Title>
			<Divider/>
			<Space>
				{'Your Role : '}
				<Tag
					color={rolesDisplay[user.role].color}
					key={user.role}
					style={{ width: 60, textAlign: 'center' }}
				>
					{rolesDisplay[user.role].name}
				</Tag>
			</Space>
			<Divider/>
			<Space>
				Your ID : {user.id}
			</Space>
			<Divider/>
			<Space>
				Your Username : {user.sub}
			</Space>
		</>
	);
}


export default ProfileDetails;
