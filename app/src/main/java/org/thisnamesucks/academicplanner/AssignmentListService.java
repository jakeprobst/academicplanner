package org.thisnamesucks.academicplanner;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by jake on 12/7/15.
 */
public class AssignmentListService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new AssignmentListProvider(this.getApplicationContext(), intent);
    }

}
