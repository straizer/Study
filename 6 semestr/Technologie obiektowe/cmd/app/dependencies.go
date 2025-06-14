package app

type dependencies struct{}

func NewDependencies(_ *Options) *dependencies {
	return &dependencies{}
}
