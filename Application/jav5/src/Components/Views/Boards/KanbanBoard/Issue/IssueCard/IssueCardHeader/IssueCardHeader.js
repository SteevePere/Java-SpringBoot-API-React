import React from 'react';
import { Divider, Space, Tooltip } from 'antd';

import { IssueTypeModel } from 'Models/IssueTypeModel';


const IssueCardHeader = (props) =>
{

	const {
		issue,
	} = props;


	return (

		<div
			style={{
				backgroundImage: 'url(' + window.location.origin + '/kanban_logo_3.jpg)',
				backgroundSize: 'cover',
				backgroundRepeat: 'no-repeat',
				backgroundPosition: '0 -60px',
				boxShadow: 'inset 0 0 0 200px rgba(255, 255, 255, 0.6)',
			}}
		>
			<h3>
				<Space
					style={{
						float: 'left',
						height: 40,
					}}
				>
					<Tooltip title={IssueTypeModel[issue.type].label}>
						{IssueTypeModel[issue.type].icon}
					</Tooltip>
					{issue.title}
				</Space>
			</h3>
			<Divider/>
		</div>
	);
}


export default IssueCardHeader;
