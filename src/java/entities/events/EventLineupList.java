/*
 * Copyright (C) 2014 Chris Pratt <www.chrispdevelopment.co.uk>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

package entities.events;

import java.util.ArrayList;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
public class EventLineupList {
    private ArrayList<EventLineup> list;

    public EventLineupList() {
        list = new ArrayList<EventLineup>();
    }

    public void addEventLineup(EventLineup newEventLineup) {
        list.add(newEventLineup);
    }

    public int size() {
        return list.size();
    }

    public EventLineup getEventLineupAt(int index) {
        if (index < 0 || index >= list.size()) {
            return null;
        } else {
            return list.get(index);
        }
    }
}
