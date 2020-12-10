import React from 'react';
import moment from 'moment';


const BoardCardFooter = (props) =>
{

	const {
		board,
	} = props;


	return (

		<>
			<li>
				<small>
					<i>
						Created on {moment(board.creationDate).format('YYYY-MM-DD')}
					</i>
				</small>
			</li>
			<li>
				<small>
					<i>
						Last updated on {moment(board.updatedDate).format('YYYY-MM-DD')}
					</i>
				</small>
			</li>
		</>
	);
}


export default BoardCardFooter;
