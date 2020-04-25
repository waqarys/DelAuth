package auth

import javax.inject.Inject
import play.api.http.{DefaultHttpFilters, EnabledFilters}

class Filters @Inject() (
                        defaultFilters: EnabledFilters,
                        auth: AuthFilter
                        ) extends DefaultHttpFilters(defaultFilters.filters :+ auth: _*)
