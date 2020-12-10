import React from 'react';
import { Card, Avatar, Tag, Tooltip } from 'antd';

import { IssuePriorityModel } from 'Models/IssuePriorityModel';

const { Meta } = Card;


const IssueCardBody = (props) =>
{

	const {
		issue,
	} = props;


	return (

		<>
			<Tooltip
				title={
					issue.assignee ?
						'Assigned to ' + issue.assignee.username :
						'Unassigned'
				}
			>
				<Meta
					avatar={
						<Avatar
							src={
								issue.assignee ? issue.assignee.isAdmin ?
									window.location.origin + '/admin_logo.png' :
									window.location.origin + '/user_logo.png' :
									window.location.origin + '/user_logo_grey.jpg'
							}
						/>
					}
					style={{ float: 'right' }}
				/>
			</Tooltip>
			<Tag
				color={IssuePriorityModel[issue.priority].color}
				style={{ float: 'left' }}
			>
				{IssuePriorityModel[issue.priority].label}
			</Tag>
		</>
	);
}


export default IssueCardBody;
