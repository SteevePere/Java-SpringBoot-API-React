import React from 'react';
import {
	UserOutlined,
	BugOutlined,
} from '@ant-design/icons';


const BoardCardBody = (props) =>
{

	const {
		board,
	} = props;


	return (

		<>
			<p>
				<UserOutlined style={{ marginRight: 5 }}/>
				{board.numberOfUsers}
			</p>
			<p>
				<BugOutlined style={{ marginRight: 5 }}/>
				{board.numberOfIssues}
			</p>
		</>
	);
}


export default BoardCardBody;
