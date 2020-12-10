import React from 'react';
import { Tag, Divider } from 'antd';
import {
	BugOutlined,
} from '@ant-design/icons';
import moment from 'moment';


const MilestoneCardBody = (props) =>
{

	const {
		milestone,
	} = props;


	const isPastDue = moment(milestone.dueDate) < moment();


	return (

		<div
			style={{ height: 200 }}
		>
			{milestone.description &&
			<>
				<p style={{ wordWrap: 'break-word' }}>
					{milestone.description}
				</p>
				<Divider/>
			</>}
			<p>
				<BugOutlined style={{ marginRight: 5 }}/>
				{milestone.numberOfIssues}
			</p>
			{milestone.dueDate &&
			<li>
				<p>
					<Tag color={isPastDue ? 'red' : 'green'}>
						Due on {moment(milestone.dueDate).format('YYYY-MM-DD')}
					</Tag>
				</p>
			</li>}
			<li>
				<i>
					Created on {moment(milestone.creationDate).format('YYYY-MM-DD')}
				</i>
			</li>
		</div>
	);
}


export default MilestoneCardBody;
