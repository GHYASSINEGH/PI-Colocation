import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostsStatisticComponent } from './posts-statistic.component';

describe('PostsStatisticComponent', () => {
  let component: PostsStatisticComponent;
  let fixture: ComponentFixture<PostsStatisticComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostsStatisticComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostsStatisticComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
