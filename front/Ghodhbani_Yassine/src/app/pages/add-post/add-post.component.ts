import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {
  success: string = '';
  employees: any[] = [];
  error: string = '';
  loading: boolean = false;

  form = new FormGroup({
    title: new FormControl('', Validators.required),
    content: new FormControl('', Validators.required),
    location: new FormControl('', Validators.required),
    
    
    posteDate: new FormControl(new Date(), Validators.required),
  
    photo: new FormControl('', Validators.required)
  });
  userId: any;

  constructor(
    private api: PostService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void { 
    this.userId = this.route.snapshot.params['userId'];
  }

  SaveAnnouncement() {
    this.error = '';
    this.loading = true;

    const announcement = this.form.value;
    const formData = new FormData();
    formData.append('announcementDto', new Blob([JSON.stringify({
      title: announcement.title,
      content: announcement.content,
      location: announcement.location,
     
      posteDate: announcement.posteDate,
      user: this.userId
    })], {
      type: 'application/json'
    }));

    if (announcement.photo) {
      formData.append('file', announcement.photo);
    }

    this.api.addAnnouncement(formData).toPromise().then((res: any) => {
      console.log(res);
      this.success = res.message;
      this.loading = false;
      this.router.navigateByUrl('/posts');
    }).catch((err: HttpErrorResponse) => {
      if (err.status === 500) {
        this.error = 'Internal Server Error';
      }
      this.loading = false;
    });
  }

  onFileChange(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.form.patchValue({
        photo: file
      });
    }
  }

}
