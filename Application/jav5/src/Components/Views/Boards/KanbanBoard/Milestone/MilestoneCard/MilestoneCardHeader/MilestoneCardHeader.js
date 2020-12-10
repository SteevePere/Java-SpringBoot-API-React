import React from 'react';
import { Divider, Space } from 'antd';


const MilestoneCardHeader = (props) =>
{

	const {
		milestone,
	} = props;


	return (

		<div
			style={{
				backgroundImage: 'url(' + window.location.origin + '/kanban_logo_2.jpg)',
				backgroundSize: 'cover',
				backgroundRepeat: 'no-repeat',
				backgroundPosition: '0 -125px',
				boxShadow: 'inset 0 0 0 200px rgba(255, 255, 255, 0.4)',
			}}
		>
			<h3>
				<Space
					style={{
						float: 'left',
						height: 70,
					}}
				>
					{milestone.title}
				</Space>
			</h3>
			<Divider/>
		</div>
	);
}


export default MilestoneCardHeader;
